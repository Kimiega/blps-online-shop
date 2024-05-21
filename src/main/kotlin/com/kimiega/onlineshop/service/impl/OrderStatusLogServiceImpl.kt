package com.kimiega.onlineshop.service.impl

import com.kimiega.onlineshop.datamapper.shop.OrderDataMapper
import com.kimiega.onlineshop.datamapper.shop.OrderStatusLogDataMapper
import com.kimiega.onlineshop.entity.EOrderStatus
import com.kimiega.onlineshop.entity.OrderStatus
import com.kimiega.onlineshop.repository.shop.OrderStatusLogRepository
import com.kimiega.onlineshop.service.OrderStatusLogService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderStatusLogServiceImpl(
    private val orderStatusLogRepository: OrderStatusLogRepository,
): OrderStatusLogService {
    override fun addNewOrderStatus(orderId: Long, status: EOrderStatus) {
        orderStatusLogRepository.save(
            OrderStatusLogDataMapper(status = status.name, date = LocalDateTime.now(), order = OrderDataMapper(id = orderId))
        )
    }


    override fun getOrderStatuses(orderId: Long): List<OrderStatus> {
        val orderStatuses = orderStatusLogRepository.findAllByOrderId(orderId)
        return orderStatuses.map {convertOrderStatusLogDataMapperToOrderStatus(it) }
    }

    private fun convertOrderStatusLogDataMapperToOrderStatus(os: OrderStatusLogDataMapper): OrderStatus {
        return OrderStatus(
            id = os.id!!,
            orderId = os.order!!.id!!,
            status = os.status!!,
            date = os.date!!,
        )
    }
}
