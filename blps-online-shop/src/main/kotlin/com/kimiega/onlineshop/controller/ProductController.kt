package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.ProductRequest
import com.kimiega.onlineshop.dto.response.ProductResponse
import com.kimiega.onlineshop.dto.response.ProductsResponse
import com.kimiega.onlineshop.service.ProductService
import io.micrometer.core.annotation.Timed
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products API")
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRODUCT')")
    @Timed(value = "product_all_getter")
    fun getProducts(): ResponseEntity<ProductsResponse> {
        val products = productService.getProducts()
        return ResponseEntity.ok(ProductsResponse(products.map{ProductResponse(it)}))
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_PRODUCT')")
    @Timed(value = "product_adder")
    fun addProduct(
        @RequestBody product: ProductRequest,
    ): ResponseEntity<Void> {
        productService.addProduct(product.name, product.description, product.price, product.count)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAuthority('READ_PRODUCT')")
    @Timed(value = "product_getter")
    fun getProduct(
        @PathVariable("productId") productId: Long
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(ProductResponse(productService.getProduct(productId)))
    }
}
