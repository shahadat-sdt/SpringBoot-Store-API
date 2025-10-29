package com.sm.sdt.springapi.carts

import java.lang.RuntimeException

class CartEmptyException : RuntimeException("Cart is empty")