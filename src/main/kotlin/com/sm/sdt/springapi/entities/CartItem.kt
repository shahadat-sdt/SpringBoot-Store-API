package com.sm.sdt.springapi.entities

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "cart_items")
class CartItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null


    @Column(name = "quantity")
    var quantity: Int? = null
}