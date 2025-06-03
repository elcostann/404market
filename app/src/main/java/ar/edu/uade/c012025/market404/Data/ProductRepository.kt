package ar.edu.uade.c012025.market404.Data

import ar.edu.uade.c012025.market404.domain.IProductRepository


class ProductRepository(
    private val dataSource: ProductApiDataSource = ProductApiDataSource()
) : IProductRepository {
    override suspend fun getAllProducts(): List<Product> = dataSource.getAllProducts()

    override suspend fun getProductById(id: Int): Product = dataSource.getProductById(id)

    override suspend fun getProductsByCategory(category: String): List<Product> =
        dataSource.getProductsByCategory(category)

    override suspend fun getCategories(): List<String> = dataSource.getCategories() }