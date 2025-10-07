package com.sm.sdt.springapi.dtos

import java.math.BigDecimal

data class CartItemDto(
    val product: CartProductDto?,
    val quantity: Int?,
    val totalPrice: BigDecimal?
)