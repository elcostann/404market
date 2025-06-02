package ar.edu.uade.c012025.market404.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: IProductAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/") //
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IProductAPI::class.java)
    }
    val cartApi: ICartAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICartAPI::class.java)
    }
}
