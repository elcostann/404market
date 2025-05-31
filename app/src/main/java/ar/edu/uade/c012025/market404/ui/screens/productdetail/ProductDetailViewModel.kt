package ar.edu.uade.c012025.market404.ui.screens.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {
    private val dataSource = ProductApiDataSource()

    private val _state = MutableStateFlow(ProductDetailScreenState())
    val state: StateFlow<ProductDetailScreenState> = _state

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val product = dataSource.getProductById(id)
                _state.value = ProductDetailScreenState(product = product)
            } catch (e: Exception) {
                _state.value = ProductDetailScreenState(error = e.message)
            }
        }
    }
}