package com.sm.sdt.springapi.products

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

@Mapper(componentModel = "Spring")
interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    fun toDto(product: Product): ProductDto

    fun toEntity(productDto: ProductDto): Product


    @Mapping(target = "id", ignore = true)
    fun update(
        productDto: ProductDto,
        @MappingTarget
        product: Product
    )
}