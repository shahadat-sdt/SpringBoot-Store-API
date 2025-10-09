package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.AddItemsToCartRequest
import com.sm.sdt.springapi.dtos.CartDto
import com.sm.sdt.springapi.dtos.CartItemDto
import com.sm.sdt.springapi.dtos.UpdateCartItemRequest
import com.sm.sdt.springapi.entities.Cart
import com.sm.sdt.springapi.mapper.CartMapper
import com.sm.sdt.springapi.repository.CartRepository
import com.sm.sdt.springapi.repository.ProductRepository
import jakarta.validation.Valid
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/carts")
class CartController(
    private val cartRepository: CartRepository,
    private val cartMapper: CartMapper,
    private val productRepository: ProductRepository,
) {

    @PostMapping
    fun addToCart(
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<CartDto> {
        val cart = Cart()
        cartRepository.save(cart)
        val cartDto = cartMapper.toDto(cart)
        val uri = uriComponentsBuilder.path("carts/{id}").buildAndExpand(cartDto.id).toUri()
        return ResponseEntity.created(uri).body(cartDto)
    }


    @PostMapping("/{cartId}/items")
    fun addToCart(
        @PathVariable cartId: UUID,
        @RequestBody request: AddItemsToCartRequest,
    ): ResponseEntity<CartItemDto> {
        val cart = cartRepository.getCartWithItems(cartId).orElse(null) ?: return ResponseEntity.notFound().build()
        val product = productRepository.findByIdOrNull(request.productId) ?: return ResponseEntity.badRequest().build()
        val cartItem = cart.addCartItem(product)
        cartRepository.save(cart)
        return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.toCartItemDto(cartItem))
    }

    @GetMapping("/{cartId}")
    fun getCartItems(
        @PathVariable cartId: UUID
    ): ResponseEntity<CartDto> {
        val cart = cartRepository.getCartWithItems(cartId).orElse(null)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(cartMapper.toDto(cart))
    }

    @PutMapping("/{cartId}/items/{productId}")
    fun updateCartItem(
        @PathVariable productId: Long,
        @PathVariable cartId: UUID,
        @RequestBody @Valid request: UpdateCartItemRequest
    ): ResponseEntity<*> {
        val cart =
            cartRepository.getCartWithItems(cartId).orElse(null) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                    mapOf("error" to "Cart Not found")
                )
        val cartItem = cart.getCartItems(productId) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            mapOf("error" to "Product Not found")
        )
        cartItem.quantity = request.quantity
        cartRepository.save(cart)
        return ResponseEntity.ok().body(cartMapper.toCartItemDto(cartItem))
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    fun deleteCartItem(
        @PathVariable cartId: UUID,
        @PathVariable productId: Long
    ): ResponseEntity<*> {
        val cart = cartRepository.getCartWithItems(cartId).orElse(null) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                    mapOf("error" to "Cart Not found")
                )

        cart.deleteItem(productId)
        cartRepository.save(cart)
        return ResponseEntity.noContent().build<Unit>()
    }

    @DeleteMapping("/{cartId}/items")
    fun clearCart(@PathVariable cartId: UUID) : ResponseEntity<Void> {

        val cart = cartRepository.getCartWithItems(cartId).orElse(null)?:return ResponseEntity.notFound().build()

        cart.clearCartItems()
        cartRepository.save(cart)
        return ResponseEntity.noContent().build()

    }
}