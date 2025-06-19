package ar.edu.uade.c012025.market404.Data

import ar.edu.uade.c012025.market404.domain.ICartRepository

class CartApiDataSource : ICartRepository {
    private val api = RetrofitInstance.cartApi

    override suspend fun createCart(cart: CartRequest): CartResponse {
        return api.createCart(cart)
    }

    override suspend fun getCartById(id: Int): CartResponse {
        return api.getCartById(id)
    }
}
