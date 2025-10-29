package com.sm.sdt.springapi.orders

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