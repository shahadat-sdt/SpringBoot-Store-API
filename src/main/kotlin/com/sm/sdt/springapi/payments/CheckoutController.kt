package com.sm.sdt.springapi.payments

import com.sm.sdt.springapi.orders.OrderRepository
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/checkout")
class CheckoutController(
    private val checkoutService: CheckoutService,


    private val orderRepository: OrderRepository
) {
    @PostMapping
    fun checkout(@RequestBody @Valid request: CheckoutRequest): CheckoutResponse {
        return checkoutService.checkout(request)
    }


    @PostMapping("/webhook")
    fun handleWebhook(@RequestHeader headers: Map<String, String>, @RequestBody payload: String) {
        checkoutService.handleWebhookEvent(WebhookRequest(headers, payload))

    }
}