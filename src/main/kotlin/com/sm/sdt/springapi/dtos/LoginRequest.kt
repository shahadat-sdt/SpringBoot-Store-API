package com.sm.sdt.springapi.dtos

import jakarta.validation.constraints.NotBlank


data class LoginRequest (
    @field:NotBlank(message = "Email can't be empty")
    val email: String,
    @field:NotBlank(message = "Password can't be empty")
    val password: String
)