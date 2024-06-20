package com.kimiega.onlineshop.security.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JWTAuthenticationFilter,
    ): DefaultSecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
//                    .requestMatchers(HttpMethod.POST,"/api/auth/login", "/api/auth/register").permitAll()
//                    .requestMatchers(HttpMethod.GET, "api/auth/guest").permitAll()
//                    .requestMatchers(HttpMethod.DELETE, "api/topic/**").permitAll()
//                    .requestMatchers(HttpMethod.PUT, "/api/topic").permitAll()
//                    .requestMatchers(HttpMethod.GET, "api/topic/**").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/api/thread").hasRole("GUEST")
//                    .requestMatchers(HttpMethod.GET, "api/thread/*/comments").permitAll()
//                    .requestMatchers(HttpMethod.GET, "api/thread/*").permitAll()
//                    .requestMatchers(HttpMethod.POST, "api/comment").hasRole("GUEST")
//                    .requestMatchers(HttpMethod.DELETE, "api/comment/*").hasRole("GUEST")
//                    .requestMatchers(HttpMethod.GET, "api/comment/*").permitAll()
//                    .requestMatchers(HttpMethod.GET, "api/media/**").permitAll()
//                    .requestMatchers(HttpMethod.DELETE, "api/media/**").hasRole("MODERATOR")
//                    .requestMatchers(HttpMethod.GET, "/api/poll/**").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/api/poll/*").hasRole("GUEST")
//                    .requestMatchers(HttpMethod.POST, "/api/reactions").hasRole("GUEST")
//                    .requestMatchers(HttpMethod.GET, "/api/reactions/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/trash/**").hasRole("MODERATOR")
//                    .requestMatchers(HttpMethod.PUT, "/api/trash").hasRole("MODERATOR")
//                    .requestMatchers("/api/admin").hasRole("ADMIN")
//                    .requestMatchers("/**").hasRole("USER")
                    .anyRequest().permitAll()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
        return http.build()
    }
}