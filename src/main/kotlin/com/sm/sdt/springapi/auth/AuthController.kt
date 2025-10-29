package com.sm.sdt.springapi.auth

import com.sm.sdt.springapi.users.LoginRequest
import com.sm.sdt.springapi.users.UserDto
import com.sm.sdt.springapi.users.UserMapper
import com.sm.sdt.springapi.users.UserRepository
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
        val user = userRepository.findByEmail(request.email) ?: throw UsernameNotFoundException("User not found")
        val accessToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)

        val cookie = Cookie("refreshToken", refreshToken.toString())
        cookie.isHttpOnly = true
        cookie.path = "/auth/refresh"
        cookie.maxAge = jwtConfig.refreshToken
        cookie.secure = true

        response.addCookie(cookie)
        return ResponseEntity.ok(JwtResponse(accessToken.toString()))
    }

    @PostMapping("/refresh")
    fun refresh(
        @CookieValue(value = "refreshToken") refreshToken: String
    ): ResponseEntity<JwtResponse> {
        val jwt = jwtService.parseToken(refreshToken)

            if (jwt == null || jwt.isExpired()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        }
        val user = userRepository.findByIdOrNull(jwt.getUserId()) ?: throw UsernameNotFoundException("User not found")
        val accessToken = jwtService.generateToken(user)

        return ResponseEntity.ok(JwtResponse(accessToken.toString()))
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