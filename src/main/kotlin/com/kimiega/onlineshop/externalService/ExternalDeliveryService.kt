package com.kimiega.onlineshop.externalService

import com.kimiega.onlineshop.entity.DeliveryDetails
import com.kimiega.onlineshop.entity.DeliveryInfo

interface ExternalDeliveryService: AcceptableDeliveryStatusService{
    fun sendPackage(deliveryDetails: DeliveryDetails): DeliveryInfo

    fun cancelDelivery(orderId: Long)
}
