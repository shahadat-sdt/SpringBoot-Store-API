package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.User
import org.springframework.data.repository.CrudRepository


interface UserRepository : CrudRepository<User, Long> {

}