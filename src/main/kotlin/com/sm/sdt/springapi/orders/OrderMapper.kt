package com.sm.sdt.springapi.orders

import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OrderMapper {
    fun toOrderDto(order: Order): OrderDto
}