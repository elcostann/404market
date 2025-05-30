package ar.edu.uade.c012025.market404.Data

import com.google.gson.Gson
import java.net.URL


class ProductApiDataSource {
    private val api = RetrofitInstance.api

    suspend fun getAllProducts(): List<Product> = api.getProducts()
    suspend fun getProductById(id: Int): Product = api.getProduct(id)
    suspend fun getProductsByCategory(category: String): List<Product> {
        return RetrofitInstance.api.getProductsByCategory(category)
    }

    suspend fun getCategories(): List<String> {
        return RetrofitInstance.api.getCategories()
    }


}
