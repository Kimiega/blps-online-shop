package com.kimiega.onlineshop.controller

import com.kimiega.onlineshop.dto.request.ProductRequest
import com.kimiega.onlineshop.dto.response.ProductResponse
import com.kimiega.onlineshop.dto.response.ProductsResponse
import com.kimiega.onlineshop.service.ProductService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
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
    val productService: ProductService,
) {
    @GetMapping
    fun getProducts(): ResponseEntity<ProductsResponse> {
        val products = productService.getProducts()
        return ResponseEntity.ok(ProductsResponse(products.map{ProductResponse(it)}))
    }

    @PostMapping
    fun addProduct(
        @RequestBody product: ProductRequest,
    ): ResponseEntity<Void> {
        productService.addProduct(product.name, product.description, product.price, product.count)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{productId}")
    fun getProduct(
        @PathVariable("productId") productId: Long
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(ProductResponse(productService.getProduct(productId)))
    }
}
