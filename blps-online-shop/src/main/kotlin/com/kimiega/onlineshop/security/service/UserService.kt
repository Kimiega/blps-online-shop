package com.kimiega.onlineshop.security.service

import com.kimiega.onlineshop.entity.AppUser
import com.kimiega.onlineshop.entity.RegisterEntity

interface UserService {
    fun findUsers() : List<AppUser>

    fun findUserByUsername(username: String) : AppUser

    fun findUserByUserId(userId: Long) : AppUser

    fun save(registerEntity : RegisterEntity): AppUser
}
