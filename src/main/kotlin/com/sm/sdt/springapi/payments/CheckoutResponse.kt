package com.sm.sdt.springapi.payments

data class CheckoutResponse(
  val orderId: Long,
  val checkoutUrl: String
)