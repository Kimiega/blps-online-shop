package com.kimiega.onlineshop.externalService

import com.kimiega.onlineshop.entity.DeliveryStatus


interface AcceptableDeliveryStatusService {
    fun acceptDeliveryStatus(delivery: DeliveryStatus)
}
