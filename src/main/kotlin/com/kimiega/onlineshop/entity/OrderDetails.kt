package com.kimiega.onlineshop.entity

data class OrdersProduct(
    val productId: Long,
    val count: Long,
)
data class OrderDetails(
    val products: List<OrdersProduct>
)
