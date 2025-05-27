package ar.edu.uade.c012025.market404.Data

import retrofit2.http.GET
import retrofit2.http.Path

interface IProductAPI {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product
}
