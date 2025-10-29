package com.sm.sdt.springapi.common

import com.sm.sdt.springapi.carts.CartEmptyException
import com.sm.sdt.springapi.carts.CartNotFoundException
import com.sm.sdt.springapi.orders.OrderAccessDeniedException
import com.sm.sdt.springapi.orders.OrderNotFoundException
import com.sm.sdt.springapi.products.ProductNotFoundException
import com.sm.sdt.springapi.payments.PaymentException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, String?>> {
        val errors = exception.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleUnreadableMessage(): ResponseEntity<ErrorDto> {
        return ResponseEntity.badRequest().body(ErrorDto("Invalid Request Body"))
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "Product not found in the Cart"))
    }

    @ExceptionHandler(CartNotFoundException::class, CartEmptyException::class)
    fun handleCartException(ex: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity.badRequest().body(ex.message?.let { ErrorDto(it) })
    }

    @ExceptionHandler(OrderNotFoundException::class)
    fun handleOrderNotFound(): ResponseEntity<Unit> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(OrderAccessDeniedException::class)
    fun handleAccessDenied(ex: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.message?.let { ErrorDto(it) })
    }

    @ExceptionHandler(PaymentException::class)
    fun handlePaymentException(ex: PaymentException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.message?.let { ErrorDto(it) })
    }
}