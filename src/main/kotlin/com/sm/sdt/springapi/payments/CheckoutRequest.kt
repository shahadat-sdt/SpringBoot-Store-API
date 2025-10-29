package com.sm.sdt.springapi.payments

import jakarta.validation.constraints.NotNull
import java.util.UUID


data class CheckoutRequest(
    @NotNull(message = "Cart Id is required")
    val cartId: UUID,
)