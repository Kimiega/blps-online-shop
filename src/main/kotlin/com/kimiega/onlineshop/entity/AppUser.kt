package com.kimiega.onlineshop.entity

data class AppUser(
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val roles: Collection<AppRole>
)
