package ar.edu.uade.c012025.market404.Data

import com.google.firebase.dataconnect.LocalDate
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
    val userId: Int= 1,
    val date:  String= LocalDate.toString(),
    val products: List<CartProduct>
)

data class CartResponse(
    val id: Int,
    val userId: Int,
    val date: String,
    val products: List<CartProduct>
)


