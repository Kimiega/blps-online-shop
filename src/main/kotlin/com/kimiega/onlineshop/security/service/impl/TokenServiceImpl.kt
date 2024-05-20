package com.kimiega.onlineshop.security.service.impl

import com.kimiega.onlineshop.exception.WrongUsernameException
import com.kimiega.onlineshop.security.configuration.JWTProperties
import com.kimiega.onlineshop.security.service.TokenService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenServiceImpl(
    jwtProperties: JWTProperties
): TokenService {

    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    override fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any>
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    override fun isSameUsername(token: String, userDetails: UserDetails) : Boolean {
        if (userDetails.username != verifyToken(token))
            throw WrongUsernameException("An attempt to use a username other than the one signed in the JWT token")
        return true
    }

    override fun verifyToken(token: String) : String? = getAllClaims(token).subject

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()
        return parser
            .parseSignedClaims(token)
            .payload
    }
}

