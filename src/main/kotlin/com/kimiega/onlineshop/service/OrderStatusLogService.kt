package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.EOrderStatus
import com.kimiega.onlineshop.entity.OrderStatus

interface OrderStatusLogService {
    fun addNewOrderStatus(orderId: Long, status: EOrderStatus)

    fun getOrderStatuses(orderId: Long): List<OrderStatus>
}
