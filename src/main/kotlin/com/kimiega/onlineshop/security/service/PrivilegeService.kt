package com.kimiega.onlineshop.security.service

import com.kimiega.onlineshop.entity.Privilege

interface PrivilegeService {
    fun addPrivilege(
        name: String,
    ): Privilege

    fun getPrivileges() : Collection<Privilege>

    fun findPrivilege(name: String): Privilege
}
