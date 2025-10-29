package com.sm.sdt.springapi.orders

import com.sm.sdt.springapi.payments.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val id: Long,
    val paymentStatus: PaymentStatus,
    val createdAt: LocalDateTime,
    val items: List<OrderItemDto>,
    val totalPrice: BigDecimal
)
