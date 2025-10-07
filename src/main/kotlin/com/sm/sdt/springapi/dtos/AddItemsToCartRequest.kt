package com.sm.sdt.springapi.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class AddItemsToCartRequest(
    @field:NotNull(message = "Product Id is required")
    val productId: Long
)