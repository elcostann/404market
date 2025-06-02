package ar.edu.uade.c012025.market404.Data

import retrofit2.http.*

class CartApiDataSource {
    private val api = RetrofitInstance.cartApi

    suspend fun createCart(cart: CartRequest): CartResponse {
        return api.createCart(cart)
    }

    suspend fun getCartById(id: Int): CartResponse {
        return api.getCartById(id)
    }
}