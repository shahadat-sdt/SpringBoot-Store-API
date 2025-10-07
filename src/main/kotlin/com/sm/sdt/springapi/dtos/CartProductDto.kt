package com.sm.sdt.springapi.dtos

import java.math.BigDecimal


data class CartProductDto(
    val id: Long?,
    val name: String?,
    val price: BigDecimal?
)
