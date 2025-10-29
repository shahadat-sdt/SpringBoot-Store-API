package com.sm.sdt.springapi.users

import jakarta.persistence.*


@Entity
@Table(name = "addresses")
class Address(
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "street")
    var street: String,

    @Column(name = "city")
    var city: String,

    @Column(name = "zip")
    var zip: String,

    @ManyToOne(fetch = FetchType.LAZY,cascade = [(CascadeType.PERSIST)])
    @JoinColumn(name = "user_id")
    var user : User? = null
){
    override fun toString(): String {
        return "Address(id=$id, street='$street', city='$city', zip='$zip')"
    }



}