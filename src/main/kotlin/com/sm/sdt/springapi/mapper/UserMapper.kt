package com.sm.sdt.springapi.mapper

import com.sm.sdt.springapi.dtos.UserDto
import com.sm.sdt.springapi.entities.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(user: User):UserDto
}