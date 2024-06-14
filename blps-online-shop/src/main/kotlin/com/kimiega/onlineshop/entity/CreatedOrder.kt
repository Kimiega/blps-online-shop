package com.kimiega.onlineshop.entity

data class CreatedOrder(
    val order: Order,
    val payment: PaymentForm,
)