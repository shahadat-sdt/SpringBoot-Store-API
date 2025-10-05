package com.sm.sdt.springapi.dtos


data class ProductDto(
    val id: Long?,
    val name: String?,
    val description: String?,
    val price: Double?,
    val categoryId: Byte?,
)