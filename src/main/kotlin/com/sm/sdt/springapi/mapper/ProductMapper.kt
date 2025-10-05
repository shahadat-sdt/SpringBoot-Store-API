package com.sm.sdt.springapi.mapper

import com.sm.sdt.springapi.dtos.ProductDto
import com.sm.sdt.springapi.entities.Product
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "Spring")
interface ProductMapper {
    @Mapping(source = "category.id",target = "categoryId")
    fun toDto(user: Product): ProductDto
}