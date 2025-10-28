package com.sm.sdt.springapi.dtos

data class CheckoutResponse(
  val orderId: Long,
  val checkoutUrl: String
)