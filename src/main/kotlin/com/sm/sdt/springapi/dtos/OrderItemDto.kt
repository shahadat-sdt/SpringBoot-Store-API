package com.sm.sdt.springapi.dtos

data class OrderItemDto (
val product : OrderProductDto,
    val quantity : Int,
    val price : Double,
)


