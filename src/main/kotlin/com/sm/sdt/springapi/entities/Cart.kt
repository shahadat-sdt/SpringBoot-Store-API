package com.sm.sdt.springapi.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "carts")
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null,

    @Column(name = "date_created", insertable = false, updatable = false)
    var dateCreated: LocalDateTime? = null,

    @OneToMany(mappedBy = "cart", cascade = [(CascadeType.MERGE)], fetch = FetchType.EAGER,orphanRemoval = true)
    var items: MutableList<CartItem> = arrayListOf()
) {

    fun calculateTotalPrice(): BigDecimal = items.sumOf { it.getTotalPrice() }

    fun addCartItem(product: Product): CartItem {

        var cartItem = items.find { it.product!!.id == product.id }

        if (cartItem != null) {
            cartItem.quantity = cartItem.quantity!! + 1
        } else {
            cartItem = CartItem(cart = this, product = product, quantity = 1)
            items.add(cartItem)
        }
        return cartItem
    }

    fun getCartItems(productId: Long): CartItem? {
        return items.find { it.product?.id == productId }
    }

    fun deleteItem(productId: Long) {
         items.removeIf { it.product?.id == productId }
    }

    fun clearCartItems() = items.clear()

    fun isEmpty() = items.isEmpty()

}