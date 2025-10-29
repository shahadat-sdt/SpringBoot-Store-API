package com.sm.sdt.springapi.auth

import com.sm.sdt.springapi.users.User
import com.sm.sdt.springapi.users.UserMapper
import com.sm.sdt.springapi.users.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val userId = authentication.principal as Long
        return userRepository.findByIdOrNull(userId) ?: throw BadCredentialsException("User not found")
    }

}