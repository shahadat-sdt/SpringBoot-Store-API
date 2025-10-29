package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.CheckoutRequest
import com.sm.sdt.springapi.dtos.CheckoutResponse
import com.sm.sdt.springapi.dtos.WebhookRequest
import com.sm.sdt.springapi.repository.OrderRepository
import com.sm.sdt.springapi.services.CheckoutService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
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