package com.kimiega.onlineshop.security.service

import com.kimiega.onlineshop.entity.AuthEntity
import com.kimiega.onlineshop.entity.LoginEntity
import com.kimiega.onlineshop.entity.RegisterEntity

interface AuthenticationService {
    fun login(loginEntity: LoginEntity): AuthEntity

    fun register(registerEntity: RegisterEntity) : AuthEntity
}
