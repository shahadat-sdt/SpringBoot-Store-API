package com.sm.sdt.springapi.users
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "profiles")
class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "bio")
    var bio: String,

    @Column(name = "phone_number")
    var phoneNumber: String,

    @Column(name = "date_of_birth")
    var dateOfBirth: LocalDate,

    @Column(name = "loyalty_points")
    var loyaltyPoints: Int,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    var user: User? = null
) {
    override fun toString(): String {
        return "Profile(id=$id, bio='$bio', phoneNumber='$phoneNumber', dateOfBirth=$dateOfBirth, loyaltyPoints=$loyaltyPoints)"
    }

}