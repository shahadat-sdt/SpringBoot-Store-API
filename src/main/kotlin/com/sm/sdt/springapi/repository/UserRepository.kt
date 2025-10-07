package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository


interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

}