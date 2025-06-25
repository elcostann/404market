package ar.edu.uade.c012025.market404.Data


data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating?
)
data class FavoriteProduct(
    val productId: String = "",
    val title: String = "",
    val image: String = "",
    val price: Double = 0.0
)

