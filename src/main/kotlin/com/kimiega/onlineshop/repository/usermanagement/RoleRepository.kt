package com.kimiega.onlineshop.repository.usermanagement

import com.kimiega.onlineshop.datamapper.usermanagement.RoleDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository: JpaRepository<RoleDataMapper, Long> {
    fun findRoleByName(name: String): Optional<RoleDataMapper>
}
