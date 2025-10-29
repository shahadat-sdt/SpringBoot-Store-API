package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.entities.PaymentStatus

data class PaymentResults (
    val orderId: Long,
    val paymentStatus: PaymentStatus
)
