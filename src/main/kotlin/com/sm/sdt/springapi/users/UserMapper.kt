package com.sm.sdt.springapi.users

import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(user: User): UserDto
    fun toEntity(request: RegisterUserRequest): User
}