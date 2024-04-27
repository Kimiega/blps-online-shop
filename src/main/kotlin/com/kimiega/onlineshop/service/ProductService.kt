package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.Product

interface ProductService : ProductGetterService {
    fun addProduct(
        name: String,
        description: String,
        price: Double,
        count: Long,
    ): Product
}
