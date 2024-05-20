package com.kimiega.onlineshop.entity

import com.kimiega.onlineshop.datamapper.OrderDataMapper


data class Order(
    val id: Long,
    val orderPrice: Double,
    val userId: Long,
    val listOfProducts: List<ProductOrder>,
) {
    constructor(orderDataMapper: OrderDataMapper) : this(
        orderDataMapper.id!!,
        orderDataMapper.orderPrice!!,
        orderDataMapper.user!!.id!!,
        orderDataMapper.listOfProducts!!.map{ProductOrder(it.id!!.productId!!, it.count!!)},
        )
}
