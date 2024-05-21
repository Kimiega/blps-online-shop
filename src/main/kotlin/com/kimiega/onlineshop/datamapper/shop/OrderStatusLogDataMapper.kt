package com.kimiega.onlineshop.datamapper.shop

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "order_status_log")
data class OrderStatusLogDataMapper(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", unique = false, nullable = false)
    val order: OrderDataMapper? = null,
    @Column(name = "status", unique = false, nullable = false, length = 255)
    val status: String? = null,
    @Column(unique = false, nullable = false)
    val date: LocalDateTime? = null,
)
