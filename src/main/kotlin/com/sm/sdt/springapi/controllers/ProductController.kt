package com.sm.sdt.springapi.controllers

import com.sm.sdt.springapi.dtos.ProductDto
import com.sm.sdt.springapi.mapper.ProductMapper
import com.sm.sdt.springapi.repository.CategoryRepository
import com.sm.sdt.springapi.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController()
@RequestMapping("/products")
class ProductController(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper,
    private val categoryRepository: CategoryRepository,
) {

    @GetMapping
    fun getAllProductsByCategory(
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

    @PostMapping
    fun createProduct(
        @RequestBody
        productDto: ProductDto,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<ProductDto> {
        val category = categoryRepository.findByIdOrNull(productDto.categoryId)
            ?: return ResponseEntity.badRequest().build()
        val product = productMapper.toEntity(productDto)
        product.category = category



        productRepository.save(product)
        val uri = uriComponentsBuilder.path("/products/add-product/{id}").buildAndExpand(product.id).toUri()
        return ResponseEntity.created(uri).body(productMapper.toDto(product))
    }

    @PutMapping("/{productId}")
    fun updateProduct(
        @PathVariable
        productId: Long,
        @RequestBody
        productDto: ProductDto,
    ): ResponseEntity<ProductDto> {
        val product = productRepository.findByIdOrNull(productId) ?: return ResponseEntity.notFound().build()
        val category =
            categoryRepository.findByIdOrNull(productDto.categoryId) ?: return ResponseEntity.badRequest().build()
        productMapper.update(productDto, product)
        product.category = category

        productRepository.save(product)
        return ResponseEntity.ok(productMapper.toDto(product))

    }


    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable
        productId: Long
    ): ResponseEntity<ProductDto> {

        val product = productRepository.findByIdOrNull(productId) ?: return ResponseEntity.notFound().build()
        productRepository.delete(product)
        return ResponseEntity.noContent().build()

    }
}