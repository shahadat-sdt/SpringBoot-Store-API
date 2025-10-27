package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
}