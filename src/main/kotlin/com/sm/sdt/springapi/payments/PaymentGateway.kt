package com.sm.sdt.springapi.payments

import com.sm.sdt.springapi.orders.Order
import java.util.Optional

interface PaymentGateway {
    fun createCheckoutSession(order: Order): CheckOutSession
    fun parseWebhookRequest(request: WebhookRequest): Optional<PaymentResults>
}