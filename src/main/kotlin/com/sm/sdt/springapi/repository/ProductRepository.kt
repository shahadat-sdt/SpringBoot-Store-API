package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {
}