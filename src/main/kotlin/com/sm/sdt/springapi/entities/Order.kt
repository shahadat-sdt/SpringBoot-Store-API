package com.sm.sdt.springapi.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: User? = null

    @Column(name = "status")
    var status: Status? = null

    @Column(name = "created_at", insertable = false, updatable = false)
    var createdAt: LocalDateTime? = null

    @Column(name = "total_price")
    var totalPrice: BigDecimal? = null

    @OneToMany(mappedBy = "order",cascade = [(CascadeType.PERSIST)])
    var items: MutableSet<OrderItem> = mutableSetOf()
}