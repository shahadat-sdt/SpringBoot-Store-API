package com.sm.sdt.springapi.carts

import jakarta.validation.constraints.NotNull


data class AddItemsToCartRequest(
    @field:NotNull(message = "Product Id is required")
    val productId: Long
)