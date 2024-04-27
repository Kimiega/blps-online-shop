package com.kimiega.onlineshop.externalService.impl

import com.kimiega.onlineshop.entity.*
import com.kimiega.onlineshop.exception.NoPaymentExistsException
import com.kimiega.onlineshop.exception.NoSuchOrderException
import com.kimiega.onlineshop.externalService.ExternalPaymentService
import com.kimiega.onlineshop.repository.PaymentRepository
import com.kimiega.onlineshop.service.OrderStatusLogService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ExternalPaymentServiceMock(
    private val paymentRepository: PaymentRepository,
    private val orderStatusLogService: OrderStatusLogService,
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

        when (payment.status) {
            PaymentStatus.PAID -> {
                orderStatusLogService.addNewOrderStatus(payment.orderId, EOrderStatus.Paid)
                paymentRepository.save(paymentInternal.get().copy(isPaid = true))

            }
            PaymentStatus.NOT_PAID -> {
                orderStatusLogService.addNewOrderStatus(payment.orderId, EOrderStatus.NotPaid)
            }
            PaymentStatus.CANCELED -> {
                orderStatusLogService.addNewOrderStatus(payment.orderId, EOrderStatus.Canceled)
            }
        }
    }
}
