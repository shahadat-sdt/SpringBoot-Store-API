package com.sm.sdt.springapi.exceptions

import java.lang.RuntimeException

class CartEmptyException : RuntimeException("Cart is empty")