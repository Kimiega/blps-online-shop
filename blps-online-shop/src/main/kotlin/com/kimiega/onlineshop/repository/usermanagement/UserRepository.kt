package com.kimiega.onlineshop.repository.usermanagement

import com.kimiega.onlineshop.datamapper.usermanagement.UserDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<UserDataMapper, Long> {
    fun findUserByUsername(username: String) : Optional<UserDataMapper>
    fun findUserById(userId: Long) : Optional<UserDataMapper>

}
