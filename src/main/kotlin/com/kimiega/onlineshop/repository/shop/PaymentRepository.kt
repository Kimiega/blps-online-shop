package com.kimiega.onlineshop.repository.shop

import com.kimiega.onlineshop.datamapper.shop.PaymentDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PaymentRepository: JpaRepository<PaymentDataMapper, Long> {
    fun findByOrderId(orderId: Long): Optional<PaymentDataMapper>
}
