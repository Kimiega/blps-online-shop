package com.kimiega.onlineshop.datamapper

import jakarta.persistence.*
@Entity(name = "orders")
data class OrderDataMapper(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "order")
    val listOfProducts : List<ProductOrderDataMapper>? = mutableListOf(),
    @Column(name = "order_price", unique = false, nullable = false, precision = 2)
    val orderPrice: Double? = null,
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = false, nullable = false)
    val user: UserDataMapper? = null,
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "order")
    val listOfStatusLog : List<OrderStatusLogDataMapper>? = mutableListOf(),
)
