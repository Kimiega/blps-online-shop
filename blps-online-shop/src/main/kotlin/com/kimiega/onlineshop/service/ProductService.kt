package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.Product
import org.springframework.transaction.annotation.Transactional

interface ProductService : ProductGetterService {
    @Transactional(rollbackFor = [Exception::class], timeout = 300)
    fun addProduct(
        name: String,
        description: String,
        price: Double,
        count: Long,
    ): Product
}
