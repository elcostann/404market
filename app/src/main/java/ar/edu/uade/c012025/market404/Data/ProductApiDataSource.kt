package ar.edu.uade.c012025.market404.Data


class ProductApiDataSource {
    private val api = RetrofitInstance.api

    suspend fun getAllProducts(): List<Product> = api.getProducts()
    suspend fun getProductById(id: Int): Product = api.getProduct(id)
}
