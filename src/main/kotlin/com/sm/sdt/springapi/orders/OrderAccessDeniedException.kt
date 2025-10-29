package com.sm.sdt.springapi.orders

class OrderAccessDeniedException : RuntimeException("You don't have permission to access this order")
