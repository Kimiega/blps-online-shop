package com.kimiega.onlineshop.service.impl

import com.kimiega.onlineshop.datamapper.ProductDataMapper
import com.kimiega.onlineshop.entity.Product
import com.kimiega.onlineshop.exception.NoSuchProductException
import com.kimiega.onlineshop.repository.ProductRepository
import com.kimiega.onlineshop.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
) : ProductService {
    override fun getProducts(): List<Product> {
        return productRepository.findAll().map {
            convertProductDataMapperToProduct(it)
        }
    }

    override fun getProduct(id: Long): Product {
        val product = productRepository.findById(id)
        if (product.isEmpty)
            throw NoSuchProductException("Product #${id} doesn't exists")
        return convertProductDataMapperToProduct(product.get())

    }

    override fun addProduct(name: String, description: String, price: Double, count: Long): Product {
        val product = productRepository.save(
            ProductDataMapper(
                name = name,
                description = description,
                price = price,
                count = count))
        return convertProductDataMapperToProduct(product)
    }

    private fun convertProductDataMapperToProduct(productDataMapper: ProductDataMapper): Product =
        Product(
            productDataMapper.id!!,
            productDataMapper.name!!,
            productDataMapper.description!!,
            productDataMapper.price!!,
            productDataMapper.count!!,
        )
}