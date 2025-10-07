package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Cart
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CartRepository : JpaRepository<Cart, UUID> {
}