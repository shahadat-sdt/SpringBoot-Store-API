package com.sm.sdt.springapi.orders

import com.sm.sdt.springapi.users.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = ["items.product"])
    @Query("select o from Order o where o.customer = :customer")
    fun getOrdersByCustomer(@Param("customer") customer: User): List<Order>

    @EntityGraph(attributePaths = ["items.product"])
    @Query("select o from Order o where o.id = :orderId")
    fun getOrderWithItems(@Param("orderId") id: Long): Order?

}