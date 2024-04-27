package com.kimiega.onlineshop.service.impl

import com.kimiega.onlineshop.datamapper.OrderDataMapper
import com.kimiega.onlineshop.datamapper.PaymentDataMapper
import com.kimiega.onlineshop.entity.Payment
import com.kimiega.onlineshop.entity.PaymentDetails
import com.kimiega.onlineshop.entity.PaymentForm
import com.kimiega.onlineshop.exception.NoPaymentExistsException
import com.kimiega.onlineshop.externalService.ExternalPaymentService
import com.kimiega.onlineshop.repository.PaymentRepository
import com.kimiega.onlineshop.service.PaymentService
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl(
    private val externalPaymentService: ExternalPaymentService,
    private val paymentRepository: PaymentRepository,
) : PaymentService {
    override fun initPayment(paymentDetails: PaymentDetails): PaymentForm {
        val data = externalPaymentService.getPaymentData(paymentDetails)
        val payment = paymentRepository.save(PaymentDataMapper(
            order = OrderDataMapper(id = paymentDetails.orderId),
            externalPaymentId = data.paymentId,
            isPaid = false,
            isRefunded = false,
        ))
        return PaymentForm(payment.id!!, payment.externalPaymentId!!, data.link)
    }

    override fun cancelPayment(paymentId: Long) {
        val payment = paymentRepository.findById(paymentId)
        if (payment.isEmpty)
            throw NoPaymentExistsException("Payment #${paymentId} doesn't exists")
        externalPaymentService.refund(payment.get().externalPaymentId!!)
        paymentRepository.save(payment.get().copy(isRefunded = true))
    }

    override fun getPaymentByOrderId(orderId: Long): Payment {
        val payment = paymentRepository.findByOrderId(orderId)
        if (payment.isEmpty)
            throw NoPaymentExistsException("Payment for order #${orderId} doesn't exists")
        return convertPaymentDataMapperToPayment(payment.get())

    }

    override fun isOrderPaid(orderId: Long): Boolean {
        val payment = paymentRepository.findByOrderId(orderId)
        if (payment.isEmpty)
            throw NoPaymentExistsException("Payment for order #${orderId} doesn't exists")
        return payment.get().isPaid!!
    }

    private fun convertPaymentDataMapperToPayment(payment: PaymentDataMapper): Payment {
        return Payment(
            id = payment.id!!,
            orderId = payment.order!!.id!!,
            externalPaymentId = payment.externalPaymentId!!,
            isPaid = payment.isPaid!!,
            isRefunded = payment.isRefunded!!,
        )
    }
}
