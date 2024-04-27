package com.kimiega.onlineshop.repository

import com.kimiega.onlineshop.datamapper.OrderStatusLogDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderStatusLogRepository: JpaRepository<OrderStatusLogDataMapper, Long> {
    fun findAllByOrderId(orderId: Long): List<OrderStatusLogDataMapper>
}
