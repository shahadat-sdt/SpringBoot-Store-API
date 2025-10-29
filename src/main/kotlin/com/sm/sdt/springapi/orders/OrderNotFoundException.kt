package com.sm.sdt.springapi.orders

import java.lang.RuntimeException

class OrderNotFoundException : RuntimeException("Order not found")
