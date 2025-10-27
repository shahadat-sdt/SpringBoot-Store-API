package com.sm.sdt.springapi.mapper

import com.sm.sdt.springapi.dtos.OrderDto
import com.sm.sdt.springapi.entities.Order
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OrderMapper {
    fun toOrderDto(order: Order): OrderDto
}