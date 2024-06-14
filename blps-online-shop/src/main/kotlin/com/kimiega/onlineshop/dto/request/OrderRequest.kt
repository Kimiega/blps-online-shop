package com.kimiega.onlineshop.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class OrdersProductRequest(
    @NotNull
    @Schema(example = "1")
    val productId: Long,
    @NotNull
    @Positive
    @Schema(example = "1")
    val count: Long,
)
data class OrderRequest(
    @NotNull
    @NotEmpty
    val products: List<OrdersProductRequest>,
)
