package com.kimiega.onlineshop.security.service.impl

import com.kimiega.onlineshop.datamapper.usermanagement.PrivilegeDataMapper
import com.kimiega.onlineshop.entity.Privilege
import com.kimiega.onlineshop.exception.NoSuchPrivilegeException
import com.kimiega.onlineshop.repository.usermanagement.PrivilegeRepository
import com.kimiega.onlineshop.security.service.PrivilegeService
import org.springframework.stereotype.Service

@Service
class PrivilegeServiceImpl(
    val privilegeRepository: PrivilegeRepository,
): PrivilegeService {
    override fun addPrivilege(name: String): Privilege {
        val privilege = privilegeRepository.save(PrivilegeDataMapper(name = name))
        return privilege.mapToPrivilege()
    }

    override fun getPrivileges(): Collection<Privilege> {
        return privilegeRepository.findAll().map {it.mapToPrivilege()}
    }

    override fun findPrivilege(name: String): Privilege {
        val privilege = privilegeRepository.findPrivilegeByName(name)
        if (privilege.isEmpty)
            throw NoSuchPrivilegeException("Privilege '$name' doesn't exists")
        return privilege.get().mapToPrivilege()
    }
    private fun PrivilegeDataMapper.mapToPrivilege(): Privilege =
        Privilege(
            id = this.id!!,
            name = this.name!!,
        )
}