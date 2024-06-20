package com.kimiega.onlineshop.entity

enum class PaymentStatus(s: String) {
    PAID("PAID"),
    NOT_PAID("NOT_PAID"),
    CANCELED("CANCELED");
}
data class PaymentResult(
    val externalPaymentId: Long,
    val orderId: Long,
    val status: PaymentStatus,
)
