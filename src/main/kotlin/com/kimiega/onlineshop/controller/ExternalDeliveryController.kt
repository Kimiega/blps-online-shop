package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.ExternalDeliveryResultRequest
import com.kimiega.onlineshop.externalService.AcceptableDeliveryStatusService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/externalDelivery")
@Tag(name = "External Delivery API")
class ExternalDeliveryController(
    val deliveryService: AcceptableDeliveryStatusService,
) {
    @PostMapping
    fun setDeliveryStatus(
        @RequestBody deliveryStatus: ExternalDeliveryResultRequest,
    ) : ResponseEntity<Void> {
        deliveryService.acceptDeliveryStatus(deliveryStatus.toDeliveryStatus())
        return ResponseEntity.ok().build()
    }
}
