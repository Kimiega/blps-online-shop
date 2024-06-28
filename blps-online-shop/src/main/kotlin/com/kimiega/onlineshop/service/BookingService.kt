package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.ProductOrder
import org.springframework.transaction.annotation.Transactional

interface BookingService {
    @Transactional(rollbackFor = [Exception::class], timeout = 300)
    fun book(products: List<ProductOrder>)
    @Transactional(rollbackFor = [Exception::class], timeout = 300)
    fun cancelBooking(products: List<ProductOrder>)
}
