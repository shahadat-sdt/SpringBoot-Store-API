package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository : JpaRepository<CartItem, Long> {
}