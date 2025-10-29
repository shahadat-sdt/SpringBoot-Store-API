package com.sm.sdt.springapi.users

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegisterUserRequest(
    @field:NotBlank(message = "Name cannot be blank")
    @field:NotNull
    @field:Size(max = 50, message = "Name must be less than 50 characters")
    val name: String?,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Email must be valid")
    val email: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    val password: String,
)