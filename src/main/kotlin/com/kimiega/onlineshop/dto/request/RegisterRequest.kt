package com.kimiega.onlineshop.dto.request

import com.kimiega.onlineshop.entity.UserType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*

enum class UserTypeRequest{
    CUSTOMER, SELLER;
    fun toUserType(): UserType = when (this) {
        SELLER -> UserType.SELLER
        CUSTOMER -> UserType.CUSTOMER
    }
}
data class RegisterRequest(
    @NotEmpty
    @NotNull
    @Size(min = 5, max = 32)
    @Schema(example = "username")
    val login: String,
    @NotEmpty
    @NotNull
    @Schema(example = "username@test.ru")
    @Email
    val email: String,
    @NotEmpty
    @NotNull
    @Size(min = 8, max = 32)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,32}\$")
    @Schema(example = "P@ssw0rd")
    val password: String,
    @NotNull
    @Schema(example = "CUSTOMER")
    val userType: UserTypeRequest,
)
