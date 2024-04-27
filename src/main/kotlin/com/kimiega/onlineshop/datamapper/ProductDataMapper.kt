package com.kimiega.onlineshop.datamapper

import jakarta.persistence.*

@Entity(name = "products")
data class ProductDataMapper(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name", unique = false, nullable = false)
    val name: String? = null,
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    val description: String? = null,
    @Column(name = "price", nullable = false, precision = 2)
    val price: Double? = null,
    @Column(name = "count", nullable = false)
    val count: Long? = null,
)
