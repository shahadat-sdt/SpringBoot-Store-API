package com.sm.sdt.springapi.dtos

data class RegisterUserRequest(
    val name: String?,
    val email: String,
    val password: String,
)