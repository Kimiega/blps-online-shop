package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.ExternalPaymentResultRequest
import com.kimiega.onlineshop.externalService.AcceptablePaymentService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/externalPayment")
@Tag(name = "External Payment API")
class ExternalPaymentController(
    val paymentService: AcceptablePaymentService,
) {
    @PostMapping
    @CrossOrigin(origins = ["http://localhost:8080"])
    fun newPayment(
        @RequestBody payment: ExternalPaymentResultRequest,
    ) : ResponseEntity<Void> {
        val internalPaymentResult = payment.toPaymentResult()
        paymentService.acceptPayment(internalPaymentResult)
        return ResponseEntity.ok().build()
    }
}
