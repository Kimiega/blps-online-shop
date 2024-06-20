package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.Product

interface ProductGetterService {
    fun getProducts(): List<Product>

    fun getProduct(id: Long): Product
}
