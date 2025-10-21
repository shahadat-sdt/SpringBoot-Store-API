package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.config.JwtConfig
import com.sm.sdt.springapi.entities.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    val jwtConfig: JwtConfig,
) {

    fun generateToken(user: User): Jwt {
        val tokenExpiration = jwtConfig.accessToken // 5m
        return generateToken(user, tokenExpiration)
    }

    fun generateRefreshToken(user: User): Jwt {
        val tokenExpiration = jwtConfig.refreshToken // 7d
        return generateToken(user, tokenExpiration)
    }


    fun getClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(jwtConfig.secretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun parseToken(token: String): Jwt? {

        try{
             val claims = getClaims(token)
            return Jwt(claims,jwtConfig.secretKey())
        }catch(e:Exception){
            return null
        }


    }


    private fun generateToken(user: User, tokenExpiration: Int): Jwt {
        val claims = Jwts
            .claims()
            .subject(user.id.toString())
            .add("email", user.email)
            .add("role", user.role.toString())
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * tokenExpiration))
            .build()
        return Jwt(claims, jwtConfig.secretKey())
    }
}

