package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Product
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = ["category"])
    fun findByCategoryId(categoryId: Long): List<Product>

    @EntityGraph(attributePaths = ["category"])
    @Query("SELECT p FROM Product p")
    fun findProductsWithCategory(): List<Product>
}