package com.kimiega.onlineshop.security.configuration

import com.kimiega.onlineshop.utils.includeErrorToHttpResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint


class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        when (authException) {
            is BadCredentialsException -> includeErrorToHttpResponse(
                HttpServletResponse.SC_BAD_REQUEST,
                "Authorization Bad Credentials",
                response,
            )

            is InsufficientAuthenticationException -> includeErrorToHttpResponse(
                HttpServletResponse.SC_FORBIDDEN,
                "Forbidden",
                response,
            )

            else -> {
                includeErrorToHttpResponse(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong!\nPlease contact with admin",
                    response,
                )
                authException.printStackTrace()
            }
        }

    }
}