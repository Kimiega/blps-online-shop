package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.*

interface OrderService {

    fun createOrder(orderDetails: OrderDetails): CreatedOrder

    fun getOrder(orderId: Long): Order

    fun sendPackage(orderId: Long, userInfo: UserInfo): DeliveryInfo

    fun getOrderStatuses(orderId: Long): List<OrderStatus>

    fun getPaymentByOrderId(orderId: Long): Payment
}
