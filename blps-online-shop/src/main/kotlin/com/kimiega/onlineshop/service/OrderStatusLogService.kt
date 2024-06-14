package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.EOrderStatus
import com.kimiega.onlineshop.entity.OrderStatus
import org.springframework.transaction.annotation.Transactional

interface OrderStatusLogService {
    @Transactional(rollbackFor = [Exception::class])
    fun addNewOrderStatus(orderId: Long, status: EOrderStatus)

    fun getOrderStatuses(orderId: Long): List<OrderStatus>
}
