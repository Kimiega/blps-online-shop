package com.kimiega.onlineshop.security.configuration

import com.kimiega.onlineshop.security.service.UserService
import com.kimiega.onlineshop.security.service.impl.CustomUserDetailsServiceImpl
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableConfigurationProperties(JWTProperties::class)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class Configuration {
    @Bean
    fun userDetailsService(
        userService: UserService,
        ): UserDetailsService =
        CustomUserDetailsServiceImpl(userService)
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
    @Bean
    fun authenticationProvider(
        userService: UserService,
        ): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userService))
                it.setPasswordEncoder(encoder())
            }
    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().addSecurityItem(
            SecurityRequirement().addList
                ("Bearer Authentication")
        )
            .components(Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
            .info(
                Info().title("My REST API")
                    .description("BLPS WEB MARKET.")
                    .version("1.0").contact(
                        Contact().name("Dmitry Belyakov")
                    )
            )
    }
    private fun createAPIKeyScheme(): SecurityScheme {
        return SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer")
    }
}

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}

