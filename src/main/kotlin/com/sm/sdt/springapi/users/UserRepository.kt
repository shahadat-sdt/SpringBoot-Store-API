package com.sm.sdt.springapi.users

import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

}