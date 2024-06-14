package com.kimiega.onlineshop.security.service.impl
import com.kimiega.onlineshop.entity.AppUser
import com.kimiega.onlineshop.security.service.RoleService
import com.kimiega.onlineshop.security.service.UserService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsServiceImpl(
    val userService: UserService,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userService.findUserByUsername(username).mapToUserDetails()
    private fun AppUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.username)
            .password(this.password)
            .roles(
                *this.roles.map {it.name}.toTypedArray()
            )
            .authorities(
                *this.roles.flatMap {it.privileges}.map { it.name }
                    .plus(this.roles.map {it.name}).toTypedArray()
            )
            .build()
}
