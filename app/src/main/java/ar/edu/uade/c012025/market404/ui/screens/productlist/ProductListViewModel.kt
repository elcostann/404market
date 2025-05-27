package ar.edu.uade.c012025.market404.ui.screens.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import ar.edu.uade.c012025.market404.Data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val dataSource = ProductApiDataSource()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                _products.value = dataSource.getAllProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}