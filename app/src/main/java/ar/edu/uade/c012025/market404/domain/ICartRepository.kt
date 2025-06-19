package ar.edu.uade.c012025.market404.domain

import ar.edu.uade.c012025.market404.Data.CartRequest
import ar.edu.uade.c012025.market404.Data.CartResponse

interface ICartRepository {
    suspend fun createCart(cart: CartRequest): CartResponse
    suspend fun getCartById(id: Int): CartResponse
}
