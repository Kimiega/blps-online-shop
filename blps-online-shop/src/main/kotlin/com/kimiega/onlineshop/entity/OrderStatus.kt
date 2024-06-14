package com.kimiega.onlineshop.entity

import java.time.LocalDateTime

enum class EOrderStatus(s: String) {
    Started("Started"),
    Booked("Booked"),
    NotBooked("Not booked"),
    Paid("Paid"),
    NotPaid("Not paid"),
    Canceled("Canceled"),
    InDelivery("In delivery"),
    NotDelivered("Not delivered"),
    Delivered("Delivered"),
    Finished("Finished"),
    Invalid("Invalid"),
}

data class OrderStatus(
    val id: Long,
    val orderId: Long,
    val status: String,
    val date: LocalDateTime,
)
