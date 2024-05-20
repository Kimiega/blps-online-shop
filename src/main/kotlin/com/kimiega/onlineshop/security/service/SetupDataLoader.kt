package com.kimiega.onlineshop.security.service

import com.kimiega.onlineshop.entity.AppRole
import com.kimiega.onlineshop.entity.Privilege
import com.kimiega.onlineshop.entity.Privileges
import com.kimiega.onlineshop.entity.Roles
import com.kimiega.onlineshop.exception.NoSuchPrivilegeException
import com.kimiega.onlineshop.exception.NoSuchRoleException
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component


@Component
class SetupDataLoader(
    val roleService: RoleService,
    val privilegeService: PrivilegeService,
) : ApplicationListener<ContextRefreshedEvent?> {
    var alreadySetup: Boolean = false

    fun createPrivilegeIfNotFound(name: String): Privilege {
        try {
            val privilege: Privilege = privilegeService.findPrivilege(name)
            return privilege
        } catch (e: NoSuchPrivilegeException) {
            return privilegeService.addPrivilege(name)
        }
    }

    fun createRoleIfNotFound(
        name: String, privileges: Collection<Privilege>): AppRole {
        try {
            val role: AppRole = roleService.findRole(name)
            return role
        } catch (e: NoSuchRoleException) {
            return roleService.addRole(name, privileges = privileges)
        }
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) return

        val privileges = Privileges.entries.map { createPrivilegeIfNotFound(it.name) }.toList()

        val sellerPrivileges = listOf(Privileges.ADD_PRODUCT, Privileges.READ_PRODUCT)
        val customerPrivileges = listOf(Privileges.MAKE_ORDER, Privileges.READ_PAYMENT, Privileges.READ_PRODUCT, Privileges.SEND_PACKAGE, Privileges.READ_ORDER, Privileges.GET_PAYMENT_FORM)
        val adminPrivileges = Privileges.entries.toList()

        createRoleIfNotFound(Roles.ADMIN.name, privileges.filter {it.name in adminPrivileges.map {privilege -> privilege.name}})
        createRoleIfNotFound(Roles.SELLER.name, privileges.filter {it.name in sellerPrivileges.map {privilege -> privilege.name}})
        createRoleIfNotFound(Roles.CUSTOMER.name, privileges.filter {it.name in customerPrivileges.map {privilege -> privilege.name}})

        alreadySetup = true
    }
}