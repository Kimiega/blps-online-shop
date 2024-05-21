package com.kimiega.onlineshop.service.impl

import com.kimiega.onlineshop.datamapper.shop.OrderDataMapper
import com.kimiega.onlineshop.datamapper.shop.ProductOrderDataMapper
import com.kimiega.onlineshop.datamapper.shop.ProductOrderKey
import com.kimiega.onlineshop.datamapper.shop.ProductDataMapper
import com.kimiega.onlineshop.entity.*
import com.kimiega.onlineshop.exception.CouldNotBeSentException
import com.kimiega.onlineshop.exception.ForbiddenException
import com.kimiega.onlineshop.exception.NoSuchOrderException
import com.kimiega.onlineshop.exception.NotEnoughProductsException
import com.kimiega.onlineshop.externalService.ExternalDeliveryService
import com.kimiega.onlineshop.repository.shop.OrderRepository
import com.kimiega.onlineshop.security.service.UserService
import com.kimiega.onlineshop.service.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val productGetterService: ProductGetterService,
    private val bookingService: BookingService,
    private val paymentService: PaymentService,
    private val orderStatusLogService: OrderStatusLogService,
    private val externalDeliveryService: ExternalDeliveryService,
    private val userService: UserService,
) : OrderService {
    override fun createOrder(orderDetails: OrderDetails): CreatedOrder {
       val userId = getUserId()
       val order = OrderDataMapper(
            orderPrice = getOrderPrice(orderDetails),
            userId = userId,
        )
        val order2 = orderRepository.save(order)
        val products = orderDetails.products
            .map { ProductOrderDataMapper(
                id = ProductOrderKey(productId = it.productId),
                count = it.count,
                order = OrderDataMapper(id = order2.id),
                product = ProductDataMapper(id = it.productId),
            )
            }

        val savedOrder = orderRepository.save(order2.copy(listOfProducts = products))
        orderStatusLogService.addNewOrderStatus(savedOrder.id!!, EOrderStatus.Started)
        bookOrder(savedOrder.id, orderDetails.products)

        val payment = createPayment(savedOrder)

        return CreatedOrder(
            order = Order(savedOrder),
            payment = payment,
        )
    }

    override fun getOrder(orderId: Long): Order {
        val order = orderRepository.findById(orderId)
        if (order.isEmpty)
            throw NoSuchOrderException("Order #${orderId} doesn't exists")
        validatePermission(order.get())
        return Order(order.get())
    }

    private fun bookOrder(orderId: Long, products: List<OrdersProduct>) {
        try {
            bookingService.book(products.map {ProductOrder(it.productId, it.count)})
            orderStatusLogService.addNewOrderStatus(orderId, EOrderStatus.Booked)
        } catch (e: NotEnoughProductsException) {
            orderStatusLogService.addNewOrderStatus(orderId, EOrderStatus.NotBooked)
            throw e
        }
    }

    private fun createPayment(order: OrderDataMapper) =
        paymentService.initPayment(PaymentDetails(order.id!!, order.orderPrice!!))

    private fun getOrderPrice(orderDetails: OrderDetails): Double =
        orderDetails.products
            .map { productGetterService.getProduct(it.productId).price * it.count }
            .foldRight(0.0, Double::plus)

    override fun sendPackage(orderId: Long): DeliveryInfo {
        val order = orderRepository.findById(orderId)
        if (order.isEmpty)
            throw NoSuchOrderException("Order #${orderId} doesn't exists")
        validatePermission(order.get())
        if (!checkIsSentable(orderId))
            throw CouldNotBeSentException("Order #${orderId} couldn't be sent")
        val userEmail = userService.findUserByUserId(getUserId()).email
        return externalDeliveryService.sendPackage(DeliveryDetails(
            orderId = order.get().id!!,
            products = order.get().listOfProducts!!.map {ProductOrder(it.id!!.productId!!, it.count!!)},
            userEmail = userEmail
        ))
    }

    override fun getOrderStatuses(orderId: Long): List<OrderStatus> {
        val order = orderRepository.findById(orderId)
        if (order.isEmpty)
            throw NoSuchOrderException("Order #${orderId} doesn't exists")
        validatePermission(order.get())
        return orderStatusLogService.getOrderStatuses(orderId)
    }

    override fun getPaymentByOrderId(orderId: Long): Payment {
        val order = orderRepository.findById(orderId)
        if (order.isEmpty)
            throw NoSuchOrderException("Order #${orderId} doesn't exists")
        validatePermission(order.get())
        return paymentService.getPaymentByOrderId(orderId)
    }

    private fun checkIsSentable(orderId: Long): Boolean {
        val statuses = orderStatusLogService.getOrderStatuses(orderId)
        if (statuses.any { it.status == EOrderStatus.Paid.name })
            if (statuses.none { it -> it.status == EOrderStatus.InDelivery.name} || statuses.none { it.status == EOrderStatus.Canceled.name})
                return true
        return false
    }

    private fun validatePermission(order: OrderDataMapper) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (!(authentication.authorities.any { it.authority == Roles.ADMIN.name } ||
                authentication.authorities.any {it.authority == Roles.CUSTOMER.name} &&
                authentication.name == userService.findUserByUserId(order.userId!!).username))
            throw ForbiddenException("You have no privileges")

    }

    private fun getUserId(): Long =
        userService.findUserByUsername(SecurityContextHolder.getContext().authentication.name).id
}
