package com.kimiega.onlineshop.repository.usermanagement

import com.kimiega.onlineshop.datamapper.usermanagement.PrivilegeDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PrivilegeRepository: JpaRepository<PrivilegeDataMapper, Long> {
    fun findPrivilegeByName(name: String): Optional<PrivilegeDataMapper>

}
