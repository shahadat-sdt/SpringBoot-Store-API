package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.dtos.WebhookRequest
import com.sm.sdt.springapi.entities.Order
import java.util.Optional

interface PaymentGateway {
    fun createCheckoutSession(order: Order): CheckOutSession
    fun parseWebhookRequest(request: WebhookRequest): Optional<PaymentResults>
}