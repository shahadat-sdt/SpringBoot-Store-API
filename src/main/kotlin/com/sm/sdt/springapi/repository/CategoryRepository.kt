package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : JpaRepository<Category, Byte> {
}