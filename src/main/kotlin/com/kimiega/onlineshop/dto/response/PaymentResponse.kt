package com.kimiega.onlineshop.dto.response

import com.kimiega.onlineshop.entity.Payment

data class PaymentResponse(
    val id: Long,
    val orderId: Long,
    val externalPaymentId: Long,
    val isPaid: Boolean,
    val isRefunded: Boolean,
) {
    constructor(payment: Payment) : this(
        id = payment.id,
        orderId = payment.orderId,
        externalPaymentId = payment.externalPaymentId,
        isPaid = payment.isPaid,
        isRefunded = payment.isRefunded,
    )
}
