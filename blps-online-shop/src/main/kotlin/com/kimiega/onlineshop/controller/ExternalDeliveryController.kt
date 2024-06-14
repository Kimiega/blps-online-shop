package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.ExternalDeliveryResultRequest
import com.kimiega.onlineshop.externalService.AcceptableDeliveryStatusService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/externalDelivery")
@Tag(name = "External Delivery API")
class ExternalDeliveryController(
    private val deliveryService: AcceptableDeliveryStatusService,
) {
    @PostMapping
    @CrossOrigin(origins = ["http://localhost:8080"])
    fun setDeliveryStatus(
        @RequestBody deliveryStatus: ExternalDeliveryResultRequest,
    ) : ResponseEntity<Void> {
        deliveryService.acceptDeliveryStatus(deliveryStatus.toDeliveryStatus())
        return ResponseEntity.ok().build()
    }
}
