package com.kimiega.onlineshop.repository

import com.kimiega.onlineshop.datamapper.OrderDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<OrderDataMapper, Long> {
}
