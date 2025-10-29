package com.sm.sdt.springapi.carts

import java.math.BigDecimal

data class CartItemDto(
    val product: CartProductDto?,
    val quantity: Int?,
    val totalPrice: BigDecimal?
)