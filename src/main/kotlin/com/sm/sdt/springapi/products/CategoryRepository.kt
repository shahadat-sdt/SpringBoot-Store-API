package com.sm.sdt.springapi.products

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Byte> {
}