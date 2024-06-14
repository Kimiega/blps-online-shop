package com.kimiega.onlineshop.repository.shop

import com.kimiega.onlineshop.datamapper.shop.OrderStatusLogDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderStatusLogRepository: JpaRepository<OrderStatusLogDataMapper, Long> {
    fun findAllByOrderId(orderId: Long): List<OrderStatusLogDataMapper>
}
