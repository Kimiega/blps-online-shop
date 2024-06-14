package com.kimiega.onlineshop.externalService

import com.kimiega.onlineshop.entity.PaymentResult

interface AcceptablePaymentService {
    fun acceptPayment(payment: PaymentResult)
}
