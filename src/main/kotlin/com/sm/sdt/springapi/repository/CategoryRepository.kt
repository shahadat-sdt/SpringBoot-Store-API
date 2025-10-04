package com.sm.sdt.springapi.repository

import com.sm.sdt.springapi.entities.Category
import org.springframework.data.repository.CrudRepository

interface CategoryRepository : CrudRepository<Category, Byte> {
}