package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.dtos.CheckoutRequest
import com.sm.sdt.springapi.dtos.CheckoutResponse
import com.sm.sdt.springapi.entities.Order
import com.sm.sdt.springapi.exceptions.CartEmptyException
import com.sm.sdt.springapi.exceptions.CartNotFoundException
import com.sm.sdt.springapi.repository.CartRepository
import com.sm.sdt.springapi.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class CheckoutService(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val authService: AuthService,
    private val cartService: CartService
) {

    fun checkout(
        request: CheckoutRequest,
    ): CheckoutResponse {
        val cart = cartRepository.getCartWithItems(request.cartId).orElse(null) ?: throw CartNotFoundException()
        if (cart.isEmpty()) throw CartEmptyException()

        val order = Order().fromCart(cart, authService.getCurrentUser())
        orderRepository.save(order)
        cart.id?.let { cartService.clearCart(it) }
        return CheckoutResponse(order.id!!)
    }
}