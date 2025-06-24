package ar.edu.uade.c012025.market404.Data

interface FavoriteRepository {
    suspend fun addFavorite(productId: Int)
    suspend fun removeFavorite(productId: Int)
    suspend fun isFavorite(productId: Int): Boolean
    suspend fun getAllFavorites(): List<Int>
}
