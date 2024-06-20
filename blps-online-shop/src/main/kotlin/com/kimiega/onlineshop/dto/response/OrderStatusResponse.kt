package com.kimiega.onlineshop.dto.response

import com.kimiega.onlineshop.entity.OrderStatus
import java.time.LocalDateTime

data class OrderStatusResponse(
    val id: Long,
    val orderId: Long,
    val status: String,
    val date: LocalDateTime,
) {
    constructor(orderStatus: OrderStatus): this(
        id = orderStatus.id,
        orderId = orderStatus.orderId,
        status = orderStatus.status,
        date = orderStatus.date,
    )
}
