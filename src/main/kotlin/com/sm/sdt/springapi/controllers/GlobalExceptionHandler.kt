package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.ErrorDto
import com.sm.sdt.springapi.exceptions.*
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
}