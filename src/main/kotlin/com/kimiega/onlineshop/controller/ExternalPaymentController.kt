package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.ExternalPaymentResultRequest
import com.kimiega.onlineshop.externalService.AcceptablePaymentService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/externalPayment")
@Tag(name = "External Payment API")
class ExternalPaymentController(
    val paymentService: AcceptablePaymentService,
) {
    @PostMapping
    fun newPayment(
        @RequestBody payment: ExternalPaymentResultRequest,
    ) : ResponseEntity<Void> {
        val internalPaymentResult = payment.toPaymentResult()
        paymentService.acceptPayment(internalPaymentResult)
        return ResponseEntity.ok().build()
    }
}
