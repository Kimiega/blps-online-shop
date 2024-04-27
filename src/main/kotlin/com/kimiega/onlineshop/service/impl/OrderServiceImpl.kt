package com.kimiega.onlineshop.service.impl

import com.kimiega.onlineshop.datamapper.OrderDataMapper
import com.kimiega.onlineshop.datamapper.ProductDataMapper
import com.kimiega.onlineshop.datamapper.ProductOrderDataMapper
import com.kimiega.onlineshop.datamapper.ProductOrderKey
import com.kimiega.onlineshop.entity.*
import com.kimiega.onlineshop.exception.NoSuchOrderException
import com.kimiega.onlineshop.exception.NotEnoughProductsException
import com.kimiega.onlineshop.externalService.ExternalDeliveryService
import com.kimiega.onlineshop.repository.OrderRepository
import com.kimiega.onlineshop.service.*
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val productGetterService: ProductGetterService,
    private val bookingService: BookingService,
    private val paymentService: PaymentService,
    private val orderStatusLogService: OrderStatusLogService,
    private val externalDeliveryService: ExternalDeliveryService,
) : OrderService {
    override fun createOrder(orderDetails: OrderDetails): CreatedOrder {
       val order = OrderDataMapper(
            orderPrice = getOrderPrice(orderDetails)
        )
        val order2 = orderRepository.save(order)
        val products = orderDetails.products
            .map { ProductOrderDataMapper(
                id = ProductOrderKey(productId = it.productId),
                count = it.count,
                order = OrderDataMapper(id = order2.id),
                product = ProductDataMapper(id = it.productId)
            )}

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

    override fun sendPackage(orderId: Long, userInfo: UserInfo): DeliveryInfo {
        //TODO check Is already sent
        val order = orderRepository.findById(orderId)
        if (order.isEmpty)
            throw NoSuchOrderException("Order #${orderId} doesn't exists")
        return externalDeliveryService.sendPackage(DeliveryDetails(
            orderId = order.get().id!!,
            products = order.get().listOfProducts!!.map {ProductOrder(it.id!!.productId!!, it.count!!)},
            userEmail = userInfo.userEmail
        ))
    }
}
