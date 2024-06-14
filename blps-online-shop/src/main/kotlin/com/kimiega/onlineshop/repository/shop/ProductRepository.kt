package com.kimiega.onlineshop.repository.shop

import com.kimiega.onlineshop.datamapper.shop.ProductDataMapper
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<ProductDataMapper, Long> {

    fun save(product: ProductDataMapper): ProductDataMapper
}
