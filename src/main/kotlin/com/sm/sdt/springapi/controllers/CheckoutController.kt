package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.CheckoutRequest
import com.sm.sdt.springapi.dtos.CheckoutResponse
import com.sm.sdt.springapi.entities.Order
import com.sm.sdt.springapi.entities.OrderItem
import com.sm.sdt.springapi.entities.Status
import com.sm.sdt.springapi.repository.CartRepository
import com.sm.sdt.springapi.repository.OrderRepository
import com.sm.sdt.springapi.services.AuthService
import com.sm.sdt.springapi.services.CartService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checkout")
class CheckoutController(
    private val cartRepository: CartRepository,
    private val authService: AuthService,
    private val orderRepository: OrderRepository,
    private val cartService: CartService,
) {
    @PostMapping
    fun checkout(
        @RequestBody
        @Valid
        request: CheckoutRequest
    ): ResponseEntity<CheckoutResponse> {
        val cart = cartRepository.getCartWithItems(request.cartId).orElse(null)
            ?: return ResponseEntity.badRequest().build()

        if (cart.items.isEmpty()) return ResponseEntity.badRequest().build()

        val order = Order()
        order.status = Status.PENDING
        order.totalPrice = cart.calculateTotalPrice()
        order.createdAt = cart.dateCreated
        order.customer = authService.getCurrentUser()
        cart.items.forEach { item ->
            val orderItem = OrderItem()
            orderItem.order = order
            orderItem.quantity = item.quantity
            orderItem.totalPrice = item.getTotalPrice()
            orderItem.product = item.product
            orderItem.unitPrice = item.product?.price
            order.items.add(orderItem)
        }

        orderRepository.save(order)
        cart.id?.let { cartService.clearCart(it) }
        return ResponseEntity.ok(CheckoutResponse(requireNotNull(order.id)))

    }
}