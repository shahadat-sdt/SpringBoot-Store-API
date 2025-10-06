package com.sm.sdt.springapi.entities

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "carts")
class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null


    @Column(name = "date_created", insertable = false, updatable = false)
    var dateCreated: LocalDate? = null

    @OneToMany(mappedBy = "cart")
    var cartItems: MutableSet<CartItem> = mutableSetOf()
}