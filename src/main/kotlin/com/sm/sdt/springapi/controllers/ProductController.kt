package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.ProductDto
import com.sm.sdt.springapi.mapper.ProductMapper
import com.sm.sdt.springapi.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/products")
class ProductController(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {

    @GetMapping
    fun getAllProducts(
        @RequestParam(name = "categoryId", required = false)
        categoryId: Long?
    ): List<ProductDto> {
        if (categoryId == null) {
            return productRepository.findProductsWithCategory().map { productMapper.toDto(it) }
        }
        return productRepository.findByCategoryId(categoryId).map { productMapper.toDto(it) }

    }

    @GetMapping("/{productId}")
    fun getProductById(
        @PathVariable
        productId: Long
    ): ResponseEntity<ProductDto> {

        val product = productRepository.findByIdOrNull(productId) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(productMapper.toDto(product))
    }
}