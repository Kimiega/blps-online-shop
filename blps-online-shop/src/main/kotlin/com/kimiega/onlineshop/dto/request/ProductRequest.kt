package com.kimiega.onlineshop.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class ProductRequest(
    @NotNull
    @Size(min = 3, max = 255)
    @Schema(example = "Шашлык")
    val name: String,
    @NotNull
    @Schema(example = "Из свинины")
    val description: String,
    @NotNull
    @PositiveOrZero
    @Schema(example = "599.99")
    val price: Double,
    @NotNull
    @PositiveOrZero
    @Schema(example = "20")
    val count: Long,
)