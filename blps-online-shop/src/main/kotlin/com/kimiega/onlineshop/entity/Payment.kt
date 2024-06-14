package com.kimiega.onlineshop.entity

data class Payment(
    val id: Long,
    val orderId: Long,
    val externalPaymentId: Long,
    val isPaid: Boolean,
    val isRefunded: Boolean,
)
