package com.sm.sdt.springapi.payments

import com.sm.sdt.springapi.orders.Order
import com.sm.sdt.springapi.carts.CartEmptyException
import com.sm.sdt.springapi.carts.CartNotFoundException
import com.sm.sdt.springapi.carts.CartRepository
import com.sm.sdt.springapi.orders.OrderRepository
import com.sm.sdt.springapi.auth.AuthService
import com.sm.sdt.springapi.carts.CartService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CheckoutService(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val authService: AuthService,
    private val cartService: CartService,
    private val paymentGateway: PaymentGateway
) {
    @Transactional
    fun checkout(request: CheckoutRequest): CheckoutResponse {
        val cart = cartRepository.getCartWithItems(request.cartId).orElse(null) ?: throw CartNotFoundException()
        if (cart.isEmpty()) throw CartEmptyException()

        val order = Order().fromCart(cart, authService.getCurrentUser())
        orderRepository.save(order)



        try {
            val session = paymentGateway.createCheckoutSession(order)
            cart.id?.let { cartService.clearCart(it) }
            return CheckoutResponse(order.id!!, session.checkOutUrl)
        } catch (ex: PaymentException) {
            orderRepository.delete(order)
            throw ex
        }
    }


    fun handleWebhookEvent(request: WebhookRequest) {
        paymentGateway.parseWebhookRequest(request).ifPresent {
            val order = orderRepository.findById(it.orderId).orElseThrow()
            order.status = it.paymentStatus
            orderRepository.save(order)
        }
    }
}