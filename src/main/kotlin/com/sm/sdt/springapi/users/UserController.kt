package com.sm.sdt.springapi.users

import jakarta.validation.Valid
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/users")

class UserController(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder,

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


    @PostMapping
    fun registerUser(
        @RequestBody @Valid request: RegisterUserRequest,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<UserDto> {

        val user = userMapper.toEntity(request)
        user.password = passwordEncoder.encode(user.password)
        user.role = Role.CUSTOMER
        userRepository.save(user)
        val uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.id).toUri()
        return ResponseEntity.created(uri).body(userMapper.toDto(user))
    }


}