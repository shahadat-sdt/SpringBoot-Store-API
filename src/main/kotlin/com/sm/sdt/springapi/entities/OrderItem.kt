package com.sm.sdt.springapi.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "order_items")
class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,


    @ManyToOne
    @JoinColumn(name = "order_id")
    var order: Order? = null,


    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,


    @Column(name = "unit_price")
    var unitPrice: BigDecimal? = null,


    @Column(name = "quantity")
    var quantity: Int? = null,

    @Column(name = "total_price")
    var totalPrice: BigDecimal? = null
)