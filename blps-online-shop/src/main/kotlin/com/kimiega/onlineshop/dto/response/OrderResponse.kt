package com.kimiega.onlineshop.dto.response

import com.kimiega.onlineshop.entity.CreatedOrder
import com.kimiega.onlineshop.entity.Order

data class ProductOrderResponse(
    val productId: Long,
    val count: Long,
)
data class OrderResponse(
    val id: Long,
    val orderPrice: Double,
    val listOfProducts: List<ProductOrderResponse>,
) {
    constructor(order: Order) : this(
        order.id,
        order.orderPrice,
        order.listOfProducts.map{ProductOrderResponse(it.productId, it.count)},
    )
}
data class PaymentDetailsResponse(
    val paymentId: Long,
    val externalPaymentId: Long,
    val link: String,
)

data class OrderCreatedResponse(
    val order: OrderResponse,
    val payment: PaymentDetailsResponse,
) {
    constructor(order: CreatedOrder) : this(
        order = OrderResponse(order.order),
        payment = PaymentDetailsResponse(order.payment.paymentId, order.payment.externalPaymentId, order.payment.link),
    )
}
