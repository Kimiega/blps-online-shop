package com.kimiega.onlineshop.datamapper.shop

import jakarta.persistence.*
@Entity(name = "payments")
data class PaymentDataMapper(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", unique = true, nullable = false)
    val order: OrderDataMapper? = null,
    @Column(unique = true, nullable = false)
    val externalPaymentId: Long? = null,
    @Column(nullable = false)
    val isPaid: Boolean? = null,
    @Column(nullable = false)
    val isRefunded: Boolean? = null,
)
