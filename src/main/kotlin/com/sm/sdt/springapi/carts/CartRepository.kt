package com.sm.sdt.springapi.carts

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*


interface CartRepository : JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = ["items.product"])
    @Query("select c from Cart c where c.id = :cartId")
    fun getCartWithItems(@Param("cartId") cartId: UUID): Optional<Cart>
}