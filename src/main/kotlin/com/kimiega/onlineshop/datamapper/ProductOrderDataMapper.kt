package com.kimiega.onlineshop.datamapper

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
class ProductOrderKey(
    @Column(name = "order_id")
    val orderId: Long? = null,
    @Column(name = "product_id")
    val productId: Long? = null,
) : Serializable

@Entity(name = "product_order")
class ProductOrderDataMapper(
    @EmbeddedId
    val id: ProductOrderKey? = null,

    @Column(name = "count", unique = false, nullable = false)
    val count: Long? = null,

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    val product: ProductDataMapper? = null,

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    val order: OrderDataMapper? = null
)
