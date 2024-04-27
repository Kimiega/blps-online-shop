package com.kimiega.onlineshop.entity

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val count: Long,
)
