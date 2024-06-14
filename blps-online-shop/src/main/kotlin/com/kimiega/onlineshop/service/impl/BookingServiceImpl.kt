package com.kimiega.onlineshop.service.impl

import com.kimiega.onlineshop.entity.ProductOrder
import com.kimiega.onlineshop.exception.NoSuchProductException
import com.kimiega.onlineshop.exception.NotEnoughProductsException
import com.kimiega.onlineshop.repository.shop.ProductRepository
import com.kimiega.onlineshop.service.BookingService
import org.springframework.stereotype.Service

@Service
class BookingServiceImpl(
    private val productRepository: ProductRepository,
): BookingService {
    override fun book(products: List<ProductOrder>) {
        val booked: MutableList<ProductOrder> = mutableListOf()
        try {
            products.forEach {
                val product = productRepository.findById(it.productId)
                if (product.isEmpty)
                    throw NoSuchProductException("Product with id #${it.productId} doesn't exists")
                if (product.get().count!! >= it.count)
                    productRepository.save(product.get().copy(count = product.get().count!! - it.count))
                else
                    throw NotEnoughProductsException("There is just ${product.get().count} of '${product.get().name}'")
                booked += it
            }
        } catch (e: NoSuchProductException) {
            cancelBooking(booked)
            throw e
        } catch (e: NotEnoughProductsException) {
            cancelBooking(booked)
            throw e
        }
    }

    override fun cancelBooking(products: List<ProductOrder>) {
        products.forEach{
            val product = productRepository.findById(it.productId)
            if (product.isPresent)
                productRepository.save(product.get().copy(count = product.get().count!! + it.count))
        }
    }
}