package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.config.JwtConfig
import com.sm.sdt.springapi.entities.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    val jwtConfig: JwtConfig,
) {

    fun generateToken(user: User): String? {
        val tokenExpiration = jwtConfig.accessToken // 5m
        return generateToken(user, tokenExpiration)
    }
    fun generateRefreshToken(user: User): String? {
        val tokenExpiration = jwtConfig.refreshToken // 7d
        return generateToken(user, tokenExpiration)
    }


    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            claims.expiration.after(Date())
        } catch (e: JwtException) {
            return false
        }
    }

    fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(jwtConfig.secretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun getUserIdFromToken(token: String): Long {
        return getClaims(token).subject.toLong()
    }

    private fun generateToken(user: User, tokenExpiration: Int): String? = Jwts
        .builder()
        .subject(user.id.toString())
        .claim("email", user.email)
        .claim("name", user.name)
        .issuedAt(Date(System.currentTimeMillis()))
        .expiration(Date(System.currentTimeMillis() + 1000 * tokenExpiration))
        .signWith(jwtConfig.secretKey())
        .compact()
}