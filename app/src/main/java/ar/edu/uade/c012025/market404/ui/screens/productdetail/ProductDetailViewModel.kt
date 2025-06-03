package ar.edu.uade.c012025.market404.ui.screens.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.CartApiDataSource
import ar.edu.uade.c012025.market404.Data.CartProduct
import ar.edu.uade.c012025.market404.Data.CartRequest
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ar.edu.uade.c012025.market404.ui.screens.carrito.CartScreenState
import ar.edu.uade.c012025.market404.Data.IProductAPI
import ar.edu.uade.c012025.market404.Data.ProductRepository
import com.google.firebase.dataconnect.LocalDate

class ProductDetailViewModel : ViewModel() {


    private val cartDataSource = CartApiDataSource()
    private val repository = ProductRepository()
    private val _cartItems = MutableStateFlow<List<CartProduct>>(emptyList())
    val cartItems: StateFlow<List<CartProduct>> = _cartItems


    private val _state = MutableStateFlow(ProductDetailScreenState())
    val state: StateFlow<ProductDetailScreenState> = _state

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val product = repository.getProductById(id)
                _state.value = ProductDetailScreenState(product = product)
            } catch (e: Exception) {
                _state.value = ProductDetailScreenState(error = e.message)
            }
        }
    }
    fun addToCart(productId: Int) {
        val currentCart = _cartItems.value.toMutableList()
        val index = currentCart.indexOfFirst { it.productId == productId }

        if (index >= 0) {
            currentCart[index] = currentCart[index].copy(quantity = currentCart[index].quantity + 1)
        } else {
            currentCart.add(CartProduct(productId = productId, quantity = 1))
        }

        _cartItems.value = currentCart

        // 🔄 Enviar al backend
        viewModelScope.launch {
            try {
                val cartRequest = CartRequest(
                    userId = 1,
                    date = "31-05-2025",
                    products = currentCart
                )
                cartDataSource.createCart(cartRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}