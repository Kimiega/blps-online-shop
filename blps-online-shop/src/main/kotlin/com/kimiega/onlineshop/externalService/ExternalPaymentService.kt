package com.kimiega.onlineshop.externalService

import com.kimiega.onlineshop.entity.PaymentDetails
import com.kimiega.onlineshop.entity.ExternalPaymentData

interface ExternalPaymentService : AcceptablePaymentService {
    fun getPaymentData(paymentDetails: PaymentDetails): ExternalPaymentData

    fun refund(paymentId: Long)
}