package ar.edu.uade.c012025.market404.Data




class ProductApiDataSource {
    private val api = RetrofitInstance.api

    suspend fun getAllProducts(): List<Product> {
       return api.getProducts()
    }
    suspend fun getProductById(id: Int): Product {
        return api.getProduct(id)
    }
    suspend fun getProductsByCategory(category: String): List<Product> {
        return api.getProductsByCategory(category)
    }

    suspend fun getCategories(): List<String> {
        return api.getCategories()
    }


}
