package ar.edu.uade.c012025.market404.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val dataSource = ProductApiDataSource()
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    private val _uiState = MutableStateFlow(ProductListScreenState())
    val uiState: StateFlow<ProductListScreenState> = _uiState

    init {
        getAllProducts()
    }


    fun getAllProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            val products = dataSource.getAllProducts()
            _uiState.update { it.copy(
                allProducts = products,
                selectedCategory = "Todos"
            ).applyFilters()}
            _isLoading.value = false
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val products = dataSource.getProductsByCategory(category)
            _uiState.update {
                it.copy(
                    allProducts = products,
                    selectedCategory = category
                ).applyFilters()
            }
            _isLoading.value = false
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update {
            it.copy(searchQuery = query).applyFilters()
        }
    }

    // Extensión para aplicar búsqueda sobre el estado
    private fun ProductListScreenState.applyFilters(): ProductListScreenState {
        val queryLower = searchQuery.lowercase()
        val filtered = allProducts.filter {
            it.title.lowercase().contains(queryLower)
        }
        return this.copy(filteredProducts = filtered)
    }
}
