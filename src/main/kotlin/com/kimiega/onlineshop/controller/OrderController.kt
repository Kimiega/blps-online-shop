package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.OrderRequest
import com.kimiega.onlineshop.dto.request.UserInfoRequest
import com.kimiega.onlineshop.dto.response.DeliveryInfoResponse
import com.kimiega.onlineshop.dto.response.OrderCreatedResponse
import com.kimiega.onlineshop.dto.response.OrderResponse
import com.kimiega.onlineshop.dto.response.OrderStatusResponse
import com.kimiega.onlineshop.entity.OrderDetails
import com.kimiega.onlineshop.entity.OrdersProduct
import com.kimiega.onlineshop.entity.UserInfo
import com.kimiega.onlineshop.service.OrderService
import com.kimiega.onlineshop.service.OrderStatusLogService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/order")
@Tag(name = "Orders API")
class OrderController(
    private val orderService: OrderService,
    private val orderStatusLogService: OrderStatusLogService,
) {

    @PostMapping
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
    fun getOrder(
        @PathVariable("orderId") orderId: Long
    ): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(OrderResponse(orderService.getOrder(orderId)))
    }

    @GetMapping("/{orderId}/statuses")
    fun getOrderStatuses(
        @PathVariable("orderId") orderId: Long
    ): ResponseEntity<List<OrderStatusResponse>> {
        return ResponseEntity.ok(orderStatusLogService.getOrderStatuses(orderId).map {OrderStatusResponse(it)})
    }

    @PostMapping("/{orderId}/send")
    fun sendPackage(
        @PathVariable("orderId") orderId: Long,
        @RequestBody userInfoRequest: UserInfoRequest,
    ): ResponseEntity<DeliveryInfoResponse> {
        val deliveryInfo = orderService.sendPackage(orderId, UserInfo(userInfoRequest.userEmail))
        return ResponseEntity.ok(DeliveryInfoResponse(deliveryInfo.id, deliveryInfo.link))
    }
}
