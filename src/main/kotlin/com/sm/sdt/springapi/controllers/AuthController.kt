package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.JwtResponse
import com.sm.sdt.springapi.dtos.LoginRequest
import com.sm.sdt.springapi.services.JwtService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest
    ): ResponseEntity<JwtResponse> {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        val token = jwtService.generateToken(request.email)
        return ResponseEntity.ok(JwtResponse(token))
    }


    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(e: Exception): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

}