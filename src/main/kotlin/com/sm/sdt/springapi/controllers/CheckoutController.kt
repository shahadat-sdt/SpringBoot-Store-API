package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.CheckoutRequest
import com.sm.sdt.springapi.dtos.CheckoutResponse
import com.sm.sdt.springapi.services.CheckoutService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checkout")
class CheckoutController(
    private val checkoutService: CheckoutService
) {
    @PostMapping
    fun checkout(@RequestBody @Valid request: CheckoutRequest): CheckoutResponse {
        return checkoutService.checkout(request)

    }
}