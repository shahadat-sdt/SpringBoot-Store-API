package com.sm.sdt.springapi.exceptions

class PaymentException(message: String = "Error creating a checkout session") : RuntimeException(message)
