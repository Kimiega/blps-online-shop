package com.kimiega.onlineshop.repository

import com.kimiega.onlineshop.datamapper.RoleDataMapper
import com.kimiega.onlineshop.datamapper.UserDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository: JpaRepository<RoleDataMapper, Long> {
    fun findRoleByName(name: String): Optional<RoleDataMapper>
}
