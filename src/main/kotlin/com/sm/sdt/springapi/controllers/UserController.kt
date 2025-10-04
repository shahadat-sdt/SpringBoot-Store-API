package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.UserDtos
import com.sm.sdt.springapi.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.EntityResponse
import javax.xml.stream.events.EntityDeclaration

@RestController
@RequestMapping("/users")

class UserController(private val userRepository: UserRepository) {

    @GetMapping
    fun getAllUsers(): Iterable<UserDtos> {
        return userRepository.findAll()
            .stream()
            .map { UserDtos(id = it.id, name = it.name, email = it.email) }
            .toList()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserDtos> {
        val user = userRepository.findByIdOrNull(id)

        if (user == null) {
            return ResponseEntity.notFound().build()
        }
        val userDtos = UserDtos(id = user.id, name = user.name, email = user.email)
        return ResponseEntity.ok(userDtos)


    }
}