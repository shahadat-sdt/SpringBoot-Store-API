package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.ErrorDto
import com.sm.sdt.springapi.dtos.OrderDto
import com.sm.sdt.springapi.exceptions.OrderAccessDeniedException
import com.sm.sdt.springapi.mapper.OrderMapper
import com.sm.sdt.springapi.repository.OrderRepository
import com.sm.sdt.springapi.services.AuthService
import com.sm.sdt.springapi.services.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService,
) {

    @GetMapping
    fun getAllOrders(): List<OrderDto> {
        return orderService.getAllOrders()
    }

    @GetMapping("/{orderId}")
    fun getOrderById(@PathVariable orderId: Long): ResponseEntity<OrderDto> {
        val orderDto = orderService.getOrderById(orderId)
        return ResponseEntity.ok(orderDto)
    }


}