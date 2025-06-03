package ar.edu.uade.c012025.market404.Data


import ar.edu.uade.c012025.market404.domain.IProductRepository

class ProductApiDataSource: IProductRepository  {
    private val api = RetrofitInstance.api

    override suspend fun getAllProducts(): List<Product> {
       return api.getProducts()
    }
   override suspend fun getProductById(id: Int): Product {
        return api.getProduct(id)
    }
    override suspend fun getProductsByCategory(category: String): List<Product> {
        return RetrofitInstance.api.getProductsByCategory(category)
    }

   override suspend fun getCategories(): List<String> {
        return RetrofitInstance.api.getCategories()
    }


}
