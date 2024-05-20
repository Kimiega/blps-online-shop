package com.kimiega.onlineshop.security.service

import com.kimiega.onlineshop.entity.AppRole
import com.kimiega.onlineshop.entity.Privilege

interface RoleService {
    fun addRole(
        name: String,
        privileges: Collection<Privilege>,
    ): AppRole

    fun getRoles() : Collection<AppRole>

    fun findRole(name: String): AppRole
}
