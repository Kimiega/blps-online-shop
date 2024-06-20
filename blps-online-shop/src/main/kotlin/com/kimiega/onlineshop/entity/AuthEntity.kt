package com.kimiega.onlineshop.entity

data class LoginEntity(
    val login: String,
    val password: String,
)

data class RegisterEntity(
    val login: String,
    val email: String,
    val password: String,
    val userType: UserType,
)

data class AuthEntity(
    val login: String,
    val accessToken: String,
)
