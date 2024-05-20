package com.kimiega.onlineshop.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class LoginRequest(
    @NotEmpty
    @NotNull
    @Schema(example = "username")
    val login: String,
    @NotEmpty
    @NotNull
    @Schema(example = "P@ssw0rd")
    val password: String,
)
