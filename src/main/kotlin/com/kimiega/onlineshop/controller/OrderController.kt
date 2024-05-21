package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.OrderRequest
import com.kimiega.onlineshop.dto.response.*
import com.kimiega.onlineshop.entity.OrderDetails
import com.kimiega.onlineshop.entity.OrdersProduct
import com.kimiega.onlineshop.service.OrderService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/order")
@Tag(name = "Orders API")
class OrderController(
    private val orderService: OrderService,
) {

    @PostMapping
    @PreAuthorize("hasAuthority('MAKE_ORDER')")
    fun createOrder(
        @RequestBody order: OrderRequest,
    ): ResponseEntity<OrderCreatedResponse> {
        val createdOrder = orderService.createOrder(OrderDetails(
            order.products.map{
                OrdersProduct(it.productId, it.count)
            }
        ))
        return ResponseEntity.ok(OrderCreatedResponse(createdOrder))
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('READ_ORDER')")
    fun getOrder(
        @PathVariable("orderId") orderId: Long
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(OrderResponse(orderService.getOrder(orderId)))
    }

    @GetMapping("/{orderId}/statuses")
    @PreAuthorize("hasAuthority('READ_ORDER')")
    fun getOrderStatuses(
        @PathVariable("orderId") orderId: Long
    ): ResponseEntity<List<OrderStatusResponse>> {
        return ResponseEntity.ok(orderService.getOrderStatuses(orderId).map {OrderStatusResponse(it)})
    }

    @PostMapping("/{orderId}/send")
    @PreAuthorize("hasAuthority('SEND_PACKAGE')")
    fun sendPackage(
        @PathVariable("orderId") orderId: Long,
    ): ResponseEntity<DeliveryInfoResponse> {
        val deliveryInfo = orderService.sendPackage(orderId)
        return ResponseEntity.ok(DeliveryInfoResponse(deliveryInfo.deliveryId, deliveryInfo.link))
    }

    @GetMapping("/{orderId}/payment")
    @PreAuthorize("hasAuthority('GET_PAYMENT_FORM')")
    fun getOrderPaymentForm(
        @PathVariable("orderId") orderId: Long
    ): ResponseEntity<PaymentResponse> {
        return ResponseEntity.ok(PaymentResponse(orderService.getPaymentByOrderId(orderId)))
    }
}
