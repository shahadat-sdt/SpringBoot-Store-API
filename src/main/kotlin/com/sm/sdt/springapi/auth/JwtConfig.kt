package com.sm.sdt.springapi.auth

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
data class JwtConfig(
    var secretKey: String = "",
    var accessToken: Int = 0,
    var refreshToken: Int = 0
) {
    fun secretKey(): SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())
}