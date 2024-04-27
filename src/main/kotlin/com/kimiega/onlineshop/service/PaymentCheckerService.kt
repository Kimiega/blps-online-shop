package com.kimiega.onlineshop.service

interface PaymentCheckerService {
    fun isOrderPaid(orderId: Long): Boolean
}