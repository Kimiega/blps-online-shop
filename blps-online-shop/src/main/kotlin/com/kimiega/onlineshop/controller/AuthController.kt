package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.LoginRequest
import com.kimiega.onlineshop.dto.request.RegisterRequest
import com.kimiega.onlineshop.dto.response.AuthenticationResponse
import com.kimiega.onlineshop.entity.LoginEntity
import com.kimiega.onlineshop.entity.RegisterEntity
import com.kimiega.onlineshop.security.service.AuthenticationService
import io.micrometer.core.annotation.Timed
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API")
class AuthController(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/login")
    @Timed(value = "authentication_login")
    fun login(
        @RequestBody @Valid loginRequest: LoginRequest,
    ): AuthenticationResponse {
        val authEntity = authenticationService.login(
            LoginEntity(
                login = loginRequest.login,
                password = loginRequest.password,
            )
        )
        return AuthenticationResponse(username = authEntity.login, accessToken = authEntity.accessToken)
    }

    @PostMapping("/register")
    @Timed(value = "authentication_register")
    fun register(
        @RequestBody @Valid registerRequest: RegisterRequest,
    ): AuthenticationResponse {
        val authEntity = authenticationService.register(
            RegisterEntity(
                login = registerRequest.login,
                email = registerRequest.email,
                password = registerRequest.password,
                userType = registerRequest.userType.toUserType()
            )
        )
        return AuthenticationResponse(username = authEntity.login, accessToken = authEntity.accessToken)
    }
}
