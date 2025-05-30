package ar.edu.uade.c012025.market404.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val dataSource = ProductApiDataSource()


    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("Todos")

    val filteredProducts: StateFlow<List<Product>> = _filteredProducts
    val searchQuery: StateFlow<String> = _searchQuery
    val selectedCategory: StateFlow<String> = _selectedCategory
    val products: StateFlow<List<Product>> = _allProducts

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            val products = dataSource.getAllProducts()
            _allProducts.value = products
            _selectedCategory.value = "Todos"
            applyFilters()
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            val products = dataSource.getProductsByCategory(category)
            _allProducts.value = products
            _selectedCategory.value = category
            applyFilters()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    private fun applyFilters() {
        val query = _searchQuery.value.lowercase()
        val filtered = _allProducts.value.filter {
            it.title.lowercase().contains(query)
        }
        _filteredProducts.value = filtered
    }
}
