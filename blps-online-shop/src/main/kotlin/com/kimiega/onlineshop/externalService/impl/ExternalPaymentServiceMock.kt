package com.kimiega.onlineshop.externalService.impl

import com.kimiega.onlineshop.entity.*
import com.kimiega.onlineshop.common.entity.Notification
import com.kimiega.onlineshop.exception.NoPaymentExistsException
import com.kimiega.onlineshop.exception.NoSuchOrderException
import com.kimiega.onlineshop.exception.NoSuchUsernameException
import com.kimiega.onlineshop.externalService.ExternalPaymentService
import com.kimiega.onlineshop.repository.shop.PaymentRepository
import com.kimiega.onlineshop.security.service.UserService
import com.kimiega.onlineshop.service.NotificationService
import com.kimiega.onlineshop.service.OrderStatusLogService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ExternalPaymentServiceMock(
    private val paymentRepository: PaymentRepository,
    private val orderStatusLogService: OrderStatusLogService,
    private val notificationService: NotificationService,
    private val userService: UserService,
) : ExternalPaymentService {
    override fun getPaymentData(paymentDetails: PaymentDetails): ExternalPaymentData
    {
        return ExternalPaymentData(Random.nextInt().toLong(), "https://google.com")
    }

    override fun refund(paymentId: Long) {
       // println("Request for refund was sent")
    }

    override fun acceptPayment(payment: PaymentResult) {
        val paymentInternal = paymentRepository.findByOrderId(payment.orderId)
        if (paymentInternal.isEmpty)
            throw NoSuchOrderException("Order #${payment.orderId} doesn't exists")
        if (payment.externalPaymentId != paymentInternal.get().externalPaymentId)
            throw NoPaymentExistsException("Payment #${payment.externalPaymentId} is incorrect")
        val email = getUserEmail(paymentInternal.get().order!!.userId!!)
        when (payment.status) {
            PaymentStatus.PAID -> {
                orderStatusLogService.addNewOrderStatus(payment.orderId, EOrderStatus.Paid)
                notificationService.sendNotification(Notification(email, "Order ${payment.orderId}", "Order has been paid"))
                paymentRepository.save(paymentInternal.get().copy(isPaid = true))

            }
            PaymentStatus.NOT_PAID -> {
                orderStatusLogService.addNewOrderStatus(payment.orderId, EOrderStatus.NotPaid)
                notificationService.sendNotification(Notification(email, "Order ${payment.orderId}", "Order has not been paid"))

            }
            PaymentStatus.CANCELED -> {
                orderStatusLogService.addNewOrderStatus(payment.orderId, EOrderStatus.Canceled)
                notificationService.sendNotification(Notification(email, "Order ${payment.orderId}", "Order has been canceled"))

            }
        }
    }

    private fun getUserEmail(userId: Long): String {
        return try {
            userService.findUserByUserId(userId).email
        } catch (_: NoSuchUsernameException) {
            ""
        }
    }
}
