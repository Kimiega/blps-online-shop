package com.kimiega.onlineshop.security.service.impl

import com.kimiega.onlineshop.datamapper.RoleDataMapper
import com.kimiega.onlineshop.datamapper.UserDataMapper
import com.kimiega.onlineshop.entity.AppRole
import com.kimiega.onlineshop.entity.AppUser
import com.kimiega.onlineshop.entity.RegisterEntity
import com.kimiega.onlineshop.entity.Roles
import com.kimiega.onlineshop.exception.NoSuchUsernameException
import com.kimiega.onlineshop.repository.UserRepository
import com.kimiega.onlineshop.security.service.RoleProvider
import com.kimiega.onlineshop.security.service.RoleService
import com.kimiega.onlineshop.security.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository : UserRepository,
    private val roleService: RoleService,
    private val encoder: PasswordEncoder,
): UserService, RoleProvider {
    override fun findUsers() : List<AppUser> = userRepository.findAll().toList().map { it.mapToAppUser() }

    override fun findUserByUsername(username: String) : AppUser {
        val usernameFound = userRepository.findUserByUsername(username)
        if (usernameFound.isEmpty)
            throw NoSuchUsernameException("Username '$username' doesn't exists")
        return usernameFound.get().mapToAppUser()
    }

    override fun findUserByUserId(userId: Long) : AppUser {
        val usernameFound = userRepository.findUserById(userId)
        if (usernameFound.isEmpty)
            throw NoSuchUsernameException("User '$userId' doesn't exists")
        return usernameFound.get().mapToAppUser()
    }

    override fun save(registerEntity: RegisterEntity): AppUser {
        val user =  userRepository.save(UserDataMapper(
            username = registerEntity.login,
            email = registerEntity.email,
            password = encoder.encode(registerEntity.password),
        ))
        return user.mapToAppUser()
    }

    override fun provideRoleToUser(role: Roles, userId: Long) {
        val userFound = userRepository.findUserById(userId)
        if (userFound.isEmpty)
            throw NoSuchUsernameException("User '$userId' doesn't exists")
        val iRole = roleService.findRole(role.name)
        userRepository.save(userFound.get().copy(roles = userFound.get().roles!!.plus(RoleDataMapper(id = iRole.id))))
    }

    private fun UserDataMapper.mapToAppUser(): AppUser =
        AppUser(
            id = this.id!!,
            username = this.username!!,
            email = this.email!!,
            password = this.password!!,
            roles = this.roles?.map { roleService.findRole(it.name!!) } ?: emptyList()
        )
}