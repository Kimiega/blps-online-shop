package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.Payment
import com.kimiega.onlineshop.entity.PaymentDetails
import com.kimiega.onlineshop.entity.PaymentForm

interface PaymentService: PaymentCheckerService {

    fun initPayment(paymentDetails: PaymentDetails): PaymentForm

    fun cancelPayment(paymentId: Long)

    fun getPaymentByOrderId(orderId: Long): Payment
}