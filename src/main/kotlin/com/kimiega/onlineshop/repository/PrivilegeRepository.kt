package com.kimiega.onlineshop.repository

import com.kimiega.onlineshop.datamapper.PrivilegeDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PrivilegeRepository: JpaRepository<PrivilegeDataMapper, Long> {
    fun findPrivilegeByName(name: String): Optional<PrivilegeDataMapper>

}
