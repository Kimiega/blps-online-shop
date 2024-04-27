package com.kimiega.onlineshop.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class UserInfoRequest(
    @Email
    @NotNull
    @Schema(example = "user@example.com")
    val userEmail: String
)
