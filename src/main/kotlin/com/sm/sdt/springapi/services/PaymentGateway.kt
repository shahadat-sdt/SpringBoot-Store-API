package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.entities.Order

interface PaymentGateway {
    fun createCheckoutSession(order: Order): CheckOutSession
}