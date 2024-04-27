package com.kimiega.onlineshop.service

import com.kimiega.onlineshop.entity.ProductOrder

interface BookingService {

    fun book(products: List<ProductOrder>)

    fun cancelBooking(products: List<ProductOrder>)
}
