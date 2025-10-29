package com.sm.sdt.springapi.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: User? = null,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: PaymentStatus? = null,

    @Column(name = "created_at", insertable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

    @Column(name = "total_price")
    var totalPrice: BigDecimal? = null,

    @OneToMany(mappedBy = "order", cascade = [(CascadeType.PERSIST),(CascadeType.REMOVE)])
    var items: MutableSet<OrderItem> = mutableSetOf()
) {

    fun fromCart(cart: Cart, customer: User): Order {
        val order = Order()
        order.customer = customer
        order.status = PaymentStatus.PENDING
        order.totalPrice = cart.calculateTotalPrice()
        cart.items.forEach { item ->
            val orderItem = OrderItem(
                order = order,
                product = item.product,
                quantity = item.quantity
            ).apply {
                totalPrice = item.getTotalPrice()
                unitPrice = item.product?.price
            }
            order.items.add(orderItem)
        }

        return order
    }

    fun isPlacedBy(user: User) = customer?.id == user.id
}