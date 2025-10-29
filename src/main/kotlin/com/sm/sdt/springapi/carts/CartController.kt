package com.sm.sdt.springapi.carts

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*


@RestController
@RequestMapping("/carts")
class CartController(
    private val cartService: CartService,
) {

    @PostMapping
    fun createCart(
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<CartDto> {
        val cartDto = cartService.createCart()
        val uri = uriComponentsBuilder.path("carts/{id}").buildAndExpand(cartDto.id).toUri()
        return ResponseEntity.created(uri).body(cartDto)
    }


    @PostMapping("/{cartId}/items")
    fun addToCart(
        @PathVariable cartId: UUID,
        @RequestBody request: AddItemsToCartRequest,
    ): ResponseEntity<CartItemDto> {

        val cartItemDto = cartService.addToCart(cartId, request.productId)
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto)
    }

    @GetMapping("/{cartId}")
    fun getCartItems(
        @PathVariable cartId: UUID
    ): CartDto {
        return cartService.getCartItems(cartId)
    }

    @PutMapping("/{cartId}/items/{productId}")
    fun updateCartItem(
        @PathVariable productId: Long,
        @PathVariable cartId: UUID,
        @RequestBody @Valid request: UpdateCartItemRequest
    ): CartItemDto {
        return cartService.updateCartItem(cartId, productId, request.quantity)
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    fun deleteCartItem(
        @PathVariable cartId: UUID,
        @PathVariable productId: Long
    ): ResponseEntity<Unit> {

        cartService.deleteCartItem(cartId, productId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{cartId}/items")
    fun clearCart(@PathVariable cartId: UUID): ResponseEntity<Void> {

        cartService.clearCart(cartId)
        return ResponseEntity.noContent().build()

    }


}