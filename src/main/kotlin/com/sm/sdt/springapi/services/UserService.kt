package com.sm.sdt.springapi.services


import com.sm.sdt.springapi.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

@Service
class UserService(private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException(
            "User not found"
        )
        return User(user.email, user.password, Collections.emptyList())
    }

}