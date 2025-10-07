package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.AddItemsToCartRequest
import com.sm.sdt.springapi.dtos.CartDto
import com.sm.sdt.springapi.dtos.CartItemDto
import com.sm.sdt.springapi.entities.Cart
import com.sm.sdt.springapi.entities.CartItem
import com.sm.sdt.springapi.mapper.CartMapper
import com.sm.sdt.springapi.repository.CartItemRepository
import com.sm.sdt.springapi.repository.CartRepository
import com.sm.sdt.springapi.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
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
    private val cartItemRepository: CartItemRepository
) {

    @PostMapping
    fun createCart(
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<CartDto> {
        val cart = Cart()
        println(cart.id.toString())
        cartRepository.save(cart)
        println(cart.id.toString())
        val cartDto = cartMapper.toDto(cart)
        println(cartDto.id.toString())
        val uri = uriComponentsBuilder.path("carts/{id}").buildAndExpand(cartDto.id).toUri()

        return ResponseEntity.created(uri).body(cartDto)
    }


    @PostMapping("/{cartId}/items")
    fun createCart(
        @PathVariable cartId: UUID,
        @RequestBody request: AddItemsToCartRequest,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<CartItemDto> {
        val cart = cartRepository.findByIdOrNull(cartId) ?: return ResponseEntity.notFound().build()
        val product = productRepository.findByIdOrNull(request.productId) ?: return ResponseEntity.badRequest().build()

        cart.items.forEach { item ->
            if (item.product!!.id == request.productId) {
                item.quantity = item.quantity!! + 1
                cartRepository.save(cart)
                return ResponseEntity.ok().body(cartMapper.toCartItemDto(item))
            }
        }
        val newItem = CartItem(cart = cart, product = product, quantity = 1)
        cart.items.add(newItem)
        cartRepository.save(cart)
        val uri = uriComponentsBuilder.path("carts/{id}").buildAndExpand(cart.id).toUri()
        return ResponseEntity.created(uri).body(cartMapper.toCartItemDto(newItem))
    }
}