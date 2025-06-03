package ar.edu.uade.c012025.market404.domain

import ar.edu.uade.c012025.market404.Data.Product


interface IProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: Int): Product
    suspend fun getProductsByCategory(category: String): List<Product>
    suspend fun getCategories(): List<String>
}