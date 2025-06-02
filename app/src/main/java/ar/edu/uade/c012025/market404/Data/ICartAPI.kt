package ar.edu.uade.c012025.market404.Data

import retrofit2.http.*


interface ICartAPI {

    @POST("carts")
    suspend fun createCart(@Body cart: CartRequest): CartResponse

    @GET("carts/{id}")
    suspend fun getCartById(@Path("id") cartId: Int): CartResponse
}

data class CartProduct(
    val productId: Int,
    val quantity: Int
)

data class CartRequest(
    val userId: Int = 1, // podés dejarlo fijo para pruebas
    val date: String = "2024-06-01",
    val products: List<CartProduct>
)

data class CartResponse(
    val id: Int,
    val userId: Int,
    val date: String,
    val products: List<CartProduct>
)


