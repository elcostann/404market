package ar.edu.uade.c012025.market404.ui.screens.favoritos

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.FavoriteRepository
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _favoriteIds = mutableStateListOf<Int>()
    val favoriteIds: List<Int> = _favoriteIds

    private val _favoriteProducts = mutableStateListOf<Product>()
    val favoriteProducts: List<Product> = _favoriteProducts

    fun loadFavorites() {
        viewModelScope.launch {
            _favoriteProducts.clear()
            val ids = favoriteRepository.getAllFavorites()
            _favoriteIds.clear()
            _favoriteIds.addAll(ids)
            ids.forEach { id ->
                try {
                    val product = productRepository.getProductById(id)
                    _favoriteProducts.add(product)
                } catch (e: Exception) {
                    // Loggear el error si un producto no se encuentra
                }
            }
        }
    }

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            if (_favoriteIds.contains(productId)) {
                favoriteRepository.removeFavorite(productId)
                _favoriteIds.remove(productId)
                _favoriteProducts.removeAll { it.id == productId }
            } else {
                favoriteRepository.addFavorite(productId)
                _favoriteIds.add(productId)
                try {
                    val product = productRepository.getProductById(productId)
                    _favoriteProducts.add(product)
                } catch (e: Exception) {
                    // Manejar error si no se puede obtener el producto
                }
            }
        }
    }

    fun isFavorite(productId: Int): Boolean {
        return _favoriteIds.contains(productId)
    }
}
