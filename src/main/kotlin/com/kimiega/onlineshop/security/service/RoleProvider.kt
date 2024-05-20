package com.kimiega.onlineshop.security.service

import com.kimiega.onlineshop.entity.Roles

interface RoleProvider {
    fun provideRoleToUser(role: Roles, userId: Long)
}