package com.sm.sdt.springapi.entities
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "price")
    var price: BigDecimal,

    @Column(name = "description")
    var description: String,

    @ManyToOne(cascade = [(CascadeType.PERSIST)])
    @JoinColumn(name = "category_id")
    var category: Category? = null,
)