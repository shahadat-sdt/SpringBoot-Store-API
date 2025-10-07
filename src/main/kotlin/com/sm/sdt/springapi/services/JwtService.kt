package com.sm.sdt.springapi.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {
    @Value("\${spring.jwt.secret}")
    lateinit var key: String
    val tokenExpiration: Int = 86400
    fun generateToken(email: String): String? {
        return Jwts
            .builder()
            .subject(email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * tokenExpiration))
            .signWith(Keys.hmacShaKeyFor(key.toByteArray()))
            .compact()

    }
}