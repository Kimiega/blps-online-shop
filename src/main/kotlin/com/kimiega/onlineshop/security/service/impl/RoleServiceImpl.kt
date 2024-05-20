package com.kimiega.onlineshop.security.service.impl

import com.kimiega.onlineshop.datamapper.PrivilegeDataMapper
import com.kimiega.onlineshop.datamapper.RoleDataMapper
import com.kimiega.onlineshop.entity.AppRole
import com.kimiega.onlineshop.entity.Privilege
import com.kimiega.onlineshop.exception.NoSuchRoleException
import com.kimiega.onlineshop.repository.RoleRepository
import com.kimiega.onlineshop.security.service.RoleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleServiceImpl(
    val roleRepository: RoleRepository,
): RoleService {
    override fun addRole(name: String, privileges: Collection<Privilege>): AppRole {
        roleRepository.save(RoleDataMapper(name = name, privileges = privileges.map {PrivilegeDataMapper(id = it.id)}))
        return findRole(name)
    }

    override fun getRoles(): Collection<AppRole> {
        return roleRepository.findAll().map {it.mapToAppRole()}
    }

    override fun findRole(name: String): AppRole {
        val role = roleRepository.findRoleByName(name)
        if (role.isEmpty)
            throw NoSuchRoleException("Role '$name' doesn't exists")
        return role.get().mapToAppRole()
    }

    private fun RoleDataMapper.mapToAppRole(): AppRole =
        AppRole(
            id = this.id!!,
            name = this.name!!,
            privileges = this.privileges!!.map {Privilege(id = it.id!!, name = it.name!!)}
        )
}
