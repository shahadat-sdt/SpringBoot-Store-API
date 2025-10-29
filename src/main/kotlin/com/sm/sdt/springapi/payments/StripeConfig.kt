package com.sm.sdt.springapi.payments

import com.stripe.Stripe
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class StripeConfig {
    @Value("\${stripe.secretKey}")
    val secretKey: String? = null

    @PostConstruct
    fun init(){
        Stripe.apiKey = secretKey
    }
}