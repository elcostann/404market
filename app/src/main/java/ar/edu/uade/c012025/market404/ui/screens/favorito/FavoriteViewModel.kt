package ar.edu.uade.c012025.market404.ui.screens.favorito



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.FavoriteProduct
import ar.edu.uade.c012025.market404.Data.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val repository = FavoriteRepository()
    private val _favorites = MutableStateFlow<List<FavoriteProduct>>(emptyList())
    val favorites: MutableStateFlow<List<FavoriteProduct>> = _favorites

    private val _state = MutableStateFlow(FavoriteScreenState())
    val state: StateFlow<FavoriteScreenState> = _state

    fun loadFavorites() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                repository.getFavorites { result ->
                    _state.value = FavoriteScreenState(favorites = result)
                    _favorites.value = result // <- Actualizás la lista observable también
                }
            } catch (e: Exception) {
                _state.value = FavoriteScreenState(error = e.message)
            }
        }
    }


    fun removeFromFavorites(productId: String) {
        repository.removeFavorite(productId)
        loadFavorites()
    }
    fun addToFavorites(product: FavoriteProduct) {
        repository.addFavorite(product)
        loadFavorites() // opcional si querés refrescar la lista
    }
}
