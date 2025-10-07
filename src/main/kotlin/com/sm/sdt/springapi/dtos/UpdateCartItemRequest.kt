package com.sm.sdt.springapi.dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull


data class UpdateCartItemRequest(
    @field:NotNull(message = "Quantity is required")
    @field:Min(1, message = "Minimum Quantity is 1")
    @field:Max(100, message = "Maximum Quantity is 100")
    val quantity: Int?
)
