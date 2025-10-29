package com.sm.sdt.springapi.carts

import com.sm.sdt.springapi.products.ProductNotFoundException
import com.sm.sdt.springapi.products.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartMapper: CartMapper,
    private val productRepository: ProductRepository
) {

    fun createCart(): CartDto {
        val cart = Cart()
        cartRepository.save(cart)
        return cartMapper.toDto(cart)
    }

    fun addToCart(cartId: UUID, productId: Long): CartItemDto {
        val cart = cartRepository.getCartWithItems(cartId).orElse(null) ?: throw CartNotFoundException()
        val product = productRepository.findByIdOrNull(productId) ?: throw ProductNotFoundException()
        val cartItem = cart.addCartItem(product)
        cartRepository.save(cart)
        return cartMapper.toCartItemDto(cartItem)
    }

    fun getCartItems(cartId: UUID): CartDto {
        val cart = cartRepository.getCartWithItems(cartId).orElse(null)
            ?: throw CartNotFoundException()
        return cartMapper.toDto(cart)
    }

    fun updateCartItem(cartId: UUID, productId : Long , quantity : Int?): CartItemDto {
        val cart =
            cartRepository.getCartWithItems(cartId).orElse(null) ?: throw CartNotFoundException()
        val cartItem = cart.getCartItems(productId) ?: throw ProductNotFoundException()
        cartItem.quantity = quantity
        cartRepository.save(cart)
        return cartMapper.toCartItemDto(cartItem)
    }

    fun deleteCartItem(cartId: UUID, productId: Long) {
         val cart =
            cartRepository.getCartWithItems(cartId).orElse(null) ?: throw CartNotFoundException()
        cart.deleteItem(productId)
        cartRepository.save(cart)
    }

    fun clearCart(cartId: UUID) {
        val cart = cartRepository.getCartWithItems(cartId).orElse(null) ?: throw CartNotFoundException()
        cart.clearCartItems()
        cartRepository.save(cart)
    }
}