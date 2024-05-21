package com.kimiega.onlineshop.repository.shop

import com.kimiega.onlineshop.datamapper.shop.OrderDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<OrderDataMapper, Long> {
}
