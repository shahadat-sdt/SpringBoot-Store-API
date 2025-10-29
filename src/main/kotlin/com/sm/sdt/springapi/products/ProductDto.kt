package com.sm.sdt.springapi.products


data class ProductDto(
    val id: Long?,
    val name: String,
    val description: String,
    val price: Double,
    val categoryId: Byte,
)