package com.kimiega.onlineshop.entity

data class PaymentForm(
    val paymentId: Long,
    val externalPaymentId: Long,
    val link: String
)
