package com.kimiega.onlineshop.externalService.impl

import com.kimiega.onlineshop.entity.*
import com.kimiega.onlineshop.exception.OrderNotPaidException
import com.kimiega.onlineshop.externalService.ExternalDeliveryService
import com.kimiega.onlineshop.service.OrderStatusLogService
import com.kimiega.onlineshop.service.PaymentCheckerService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ExternalDeliveryServiceMock(
    private val paymentCheckerService: PaymentCheckerService,
    private val orderStatusLogService: OrderStatusLogService,
) : ExternalDeliveryService {
    override fun sendPackage(deliveryDetails: DeliveryDetails): DeliveryInfo {
        if (!paymentCheckerService.isOrderPaid(deliveryDetails.orderId))
            throw OrderNotPaidException("Order #${deliveryDetails.orderId} is not paid")
        orderStatusLogService.addNewOrderStatus(deliveryDetails.orderId, EOrderStatus.InDelivery)
       return DeliveryInfo(Random.nextInt().toLong(), "https://ya.ru")
    }

    override fun cancelDelivery(orderId: Long) {
        println("Delivery canceled")
    }

    override fun acceptDeliveryStatus(delivery: DeliveryStatus) {
        when (delivery.status) {
            EDeliveryStatus.DELIVERED -> {
                orderStatusLogService.addNewOrderStatus(delivery.orderId, EOrderStatus.Delivered)
            }

            EDeliveryStatus.NOT_DELIVERED -> {
                orderStatusLogService.addNewOrderStatus(delivery.orderId, EOrderStatus.NotDelivered)
            }

            EDeliveryStatus.CANCELED -> {
                orderStatusLogService.addNewOrderStatus(delivery.orderId, EOrderStatus.Canceled)
            }
        }
    }
}
