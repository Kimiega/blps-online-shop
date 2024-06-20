package com.kimiega.onlineshop.entity

data class DeliveryDetails(
    val orderId: Long,
    val products: List<ProductOrder>,
    val userEmail: String,
)
