package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.entities.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.*
import javax.crypto.SecretKey

class Jwt(
    private val claims: Claims,
    private val secret: SecretKey,
) {

    fun getUserId() = claims.subject.toLong()
    fun getRoles() = Role.valueOf(claims.get("role", String::class.java))
    fun isExpired(): Boolean = claims.expiration.before(Date())

    override fun toString(): String {
        return Jwts.builder().claims(claims).signWith(secret).compact()
    }


}