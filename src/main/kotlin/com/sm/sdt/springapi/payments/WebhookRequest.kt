package com.sm.sdt.springapi.payments

data class WebhookRequest(
    val headers: Map<String, String>,
    val payload: String
)