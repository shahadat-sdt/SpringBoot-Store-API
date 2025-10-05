package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.UserDto
import com.sm.sdt.springapi.mapper.UserMapper
import com.sm.sdt.springapi.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")

class UserController(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    @GetMapping
    fun getAllUsers(
        @RequestParam(required = false, name = "sort") sort: String = "name"
    ): Iterable<UserDto> {
        return userRepository.findAll(Sort.by(sort))
            .stream()
            .map { userMapper.toDto(it) }
            .toList()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserDto> {
        val user = userRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(userMapper.toDto(user))


    }
}