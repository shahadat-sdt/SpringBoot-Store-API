package com.sm.sdt.springapi.dtos

data class WebhookRequest(
    val headers: Map<String, String>,
    val payload: String
)