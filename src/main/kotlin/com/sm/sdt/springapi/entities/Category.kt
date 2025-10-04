package com.sm.sdt.springapi.entities

import jakarta.persistence.*

@Entity
@Table(name = "categories")
class Category (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Byte? = null,

    @Column(name = "name")
    var name: String,

    @OneToMany(mappedBy = "category")
    var products: MutableSet<Product> = mutableSetOf()
)