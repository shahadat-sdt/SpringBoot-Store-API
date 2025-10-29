package com.sm.sdt.springapi.users
import com.sm.sdt.springapi.products.Product
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @OneToMany(mappedBy = "user",  cascade = [(CascadeType.PERSIST),(CascadeType.REMOVE)], orphanRemoval = true)
    var addresses: MutableList<Address> = mutableListOf(),

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role : Role,

    @OneToOne(mappedBy = "user",cascade = [(CascadeType.REMOVE)])
    var profile: Profile? = null,

    @ManyToMany()
    @JoinTable(
        name = "wishlist",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    var wishList: MutableSet<Product> = hashSetOf()

) {

    fun addFavouriteProduct(product: Product) {
        wishList.add(product)
    }
    fun addAddress(address: Address) {
        addresses.add(address)
        address.user = this
    }

    fun removeAddress(address: Address) {
        addresses.remove(address)
        address.user = null
    }

//    fun addProfile(profile: Profile) {
//        this.profile = profile
//        profile.user= this
//    }

    override fun toString(): String {
        return javaClass.simpleName + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "email = " + email + ")"
    }

}
