package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.config.JwtConfig
import com.sm.sdt.springapi.dtos.JwtResponse
import com.sm.sdt.springapi.dtos.LoginRequest
import com.sm.sdt.springapi.dtos.UserDto
import com.sm.sdt.springapi.mapper.UserMapper
import com.sm.sdt.springapi.repository.UserRepository
import com.sm.sdt.springapi.services.JwtService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val jwtService: JwtService,
    private val jwtConfig: JwtConfig
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest,
        response: HttpServletResponse
    ): ResponseEntity<JwtResponse> {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        val user = userRepository.findByEmail(request.email)?: throw UsernameNotFoundException("User not found")
        val accessToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)

        val cookie = Cookie("refreshToken", refreshToken)
        cookie.isHttpOnly = true
        cookie.path = "/auth/refresh"
        cookie.maxAge = jwtConfig.refreshToken
       // cookie.secure = true

        response.addCookie(cookie)
        return ResponseEntity.ok(JwtResponse(accessToken))
    }

    @PostMapping("/validate")
    fun validateToken(@RequestHeader("Authorization") authHeader: String): Boolean {
        val token = authHeader.replace("Bearer ", "")
        return jwtService.validateToken(token)
    }


     @GetMapping("/me")
    fun me(): UserDto {

        val authentication = SecurityContextHolder.getContext().authentication
        val userId = authentication.principal as Long
        val user = userRepository.findByIdOrNull(userId) ?: throw BadCredentialsException("User not found")
        return userMapper.toDto(user)
    }



    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(e: Exception): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

}