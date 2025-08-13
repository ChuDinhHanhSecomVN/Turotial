package com.example.day13_8_25.data.repository

import com.example.day13_8_25.data.model.Product
import com.example.day13_8_25.data.remote.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService) {
    suspend fun fetchProducts(): List<Product> {
        return productService.getProducts()
    }
}