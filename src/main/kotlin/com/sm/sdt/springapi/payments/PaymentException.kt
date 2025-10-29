package com.sm.sdt.springapi.payments

class PaymentException(message: String = "Error creating a checkout session") : RuntimeException(message)
