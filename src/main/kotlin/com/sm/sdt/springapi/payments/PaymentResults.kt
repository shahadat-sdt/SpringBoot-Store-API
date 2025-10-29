package com.sm.sdt.springapi.payments

data class PaymentResults (
    val orderId: Long,
    val paymentStatus: PaymentStatus
)
