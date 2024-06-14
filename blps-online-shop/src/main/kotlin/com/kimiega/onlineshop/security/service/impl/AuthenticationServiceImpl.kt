package com.kimiega.onlineshop.security.service.impl

import com.kimiega.onlineshop.entity.*
import com.kimiega.onlineshop.exception.DuplicatedUsernameException
import com.kimiega.onlineshop.exception.NoSuchUsernameException
import com.kimiega.onlineshop.security.configuration.JWTProperties
import com.kimiega.onlineshop.security.service.AuthenticationService
import com.kimiega.onlineshop.security.service.RoleProvider
import com.kimiega.onlineshop.security.service.TokenService
import com.kimiega.onlineshop.security.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationServiceImpl(
    private val authManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JWTProperties,
    private val userService: UserService,
    private val roleProvider: RoleProvider,
): AuthenticationService {
    override fun login(loginEntity: LoginEntity): AuthEntity {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginEntity.login,
                loginEntity.password
            )
        )
        val user = userDetailsService.loadUserByUsername(loginEntity.login)
        val accessToken = createAccessToken(user)
        return AuthEntity(
            user.username,
            accessToken = accessToken,
        )
    }

    override fun register(registerEntity: RegisterEntity) : AuthEntity {
        try {
            userService.findUserByUsername(registerEntity.login)
            throw DuplicatedUsernameException("Username '${registerEntity.login}' is already taken")
        } catch (e : NoSuchUsernameException) {
            val user0 = userService.save(registerEntity)
            when (registerEntity.userType) {
                UserType.CUSTOMER -> roleProvider.provideRoleToUser(Roles.CUSTOMER, user0.id)
                UserType.SELLER -> roleProvider.provideRoleToUser(Roles.SELLER, user0.id)
            }
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    registerEntity.login,
                    registerEntity.password
                )
            )
            val user = userDetailsService.loadUserByUsername(registerEntity.login)
            val accessToken = createAccessToken(user)
            return AuthEntity(
                user.username,
                accessToken = accessToken,
            )
        }
    }

    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration(),
        additionalClaims = mapOf(Pair("authorities", user.authorities.map{it.authority}))
    )
    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
}