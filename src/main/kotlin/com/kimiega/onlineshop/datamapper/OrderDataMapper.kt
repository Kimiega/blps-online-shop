package com.kimiega.onlineshop.datamapper

import jakarta.persistence.*
@Entity(name = "orders")
data class OrderDataMapper(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "order")
    val listOfProducts : List<ProductOrderDataMapper>? = null,
    @Column(name = "order_price", unique = false, nullable = false, precision = 2)
    val orderPrice: Double? = null,
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "order")
    val listOfStatusLog : List<OrderStatusLogDataMapper>? = null,
)
