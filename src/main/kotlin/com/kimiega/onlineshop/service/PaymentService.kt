package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.Payment
import com.kimiega.onlineshop.entity.PaymentDetails
import com.kimiega.onlineshop.entity.PaymentForm
import org.springframework.transaction.annotation.Transactional

interface PaymentService: PaymentCheckerService {
    @Transactional(rollbackFor = [Exception::class])
    fun initPayment(paymentDetails: PaymentDetails): PaymentForm
    @Transactional(rollbackFor = [Exception::class])

    fun cancelPayment(paymentId: Long)

    fun getPaymentByOrderId(orderId: Long): Payment
}