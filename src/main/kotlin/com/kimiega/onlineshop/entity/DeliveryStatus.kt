package com.kimiega.onlineshop.entity

enum class EDeliveryStatus(s: String) {
    DELIVERED("Delivered"),
    NOT_DELIVERED("Not delivered"),
    CANCELED("CANCELED");
}
data class DeliveryStatus(
    val orderId: Long,
    val status: EDeliveryStatus,
)
