package com.sm.sdt.springapi.orders

data class OrderItemDto (
    val product : OrderProductDto,
    val quantity : Int,
    val price : Double,
)


