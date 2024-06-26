package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.*
import org.springframework.transaction.annotation.Transactional

interface OrderService {

    @Transactional(rollbackFor = [Exception::class], timeout = 300)
    fun createOrder(orderDetails: OrderDetails): CreatedOrder

    fun getOrder(orderId: Long): Order

    @Transactional(rollbackFor = [Exception::class], timeout = 300)
    fun sendPackage(orderId: Long): DeliveryInfo

    fun getOrderStatuses(orderId: Long): List<OrderStatus>

    fun getPaymentByOrderId(orderId: Long): Payment
}
