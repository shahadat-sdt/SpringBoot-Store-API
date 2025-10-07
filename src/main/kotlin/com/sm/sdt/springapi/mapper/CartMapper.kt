package com.sm.sdt.springapi.mapper

import com.sm.sdt.springapi.dtos.CartDto
import com.sm.sdt.springapi.dtos.CartItemDto
import com.sm.sdt.springapi.entities.Cart
import com.sm.sdt.springapi.entities.CartItem
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CartMapper {


    fun toEntity(cartDto: CartDto): Cart


    @Mapping(expression ="java(cart.calculateTotalPrice())" , target = "totalPrice")
    fun toDto(cart: Cart): CartDto


    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    fun toCartItemDto(cartItem: CartItem): CartItemDto

}