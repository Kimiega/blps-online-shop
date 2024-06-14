package com.kimiega.onlineshop.dto.response

import com.kimiega.onlineshop.entity.Product

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val count: Long,
) {
    constructor(product: Product) : this(
        product.id,
        product.name,
        product.description,
        product.price,
        product.count,
    )
}
