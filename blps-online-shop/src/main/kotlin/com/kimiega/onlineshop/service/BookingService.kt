package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.ProductOrder
import org.springframework.transaction.annotation.Transactional

interface BookingService {
    @Transactional(rollbackFor = [Exception::class])
    fun book(products: List<ProductOrder>)
    @Transactional(rollbackFor = [Exception::class])
    fun cancelBooking(products: List<ProductOrder>)
}
