package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.response.PaymentResponse
import com.kimiega.onlineshop.service.PaymentService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/order")
@Tag(name = "Payment API")
class PaymentController(
    private val paymentService: PaymentService,
) {
    @GetMapping("/{orderId}/payment")
    fun getOrderPaymentForm(
        @PathVariable("orderId") orderId: Long
    ): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(PaymentResponse(paymentService.getPaymentByOrderId(orderId)))
    }
}
