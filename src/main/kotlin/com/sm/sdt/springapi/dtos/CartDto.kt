package com.sm.sdt.springapi.dtos

import com.sm.sdt.springapi.entities.CartItem
import jakarta.persistence.Column
import java.math.BigDecimal
import java.util.*

data class CartDto(
    var id: UUID? = null,
    val items: MutableSet<CartItem> = mutableSetOf(),
    val price: BigDecimal = BigDecimal.ZERO ,
)