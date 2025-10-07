package com.sm.sdt.springapi.dtos

import java.math.BigDecimal
import java.util.*

data class CartDto(
    var id: UUID? = null,
    var items: MutableList<CartItemDto> = arrayListOf(),
    var totalPrice: BigDecimal = BigDecimal.ZERO,
)