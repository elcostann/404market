package ar.edu.uade.c012025.market404.ui.screens.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.uade.c012025.market404.Data.CartProduct
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CartItemUI(
    val productId: Int,
    val title: String,
    val image: String,
    val price: Double,
    val quantity: Int
)

class CartViewModel : ViewModel() {

    private val productDataSource = ProductApiDataSource()

    private val _cartItems = MutableStateFlow<List<CartProduct>>(emptyList())
    private val _state = MutableStateFlow(CartScreenState())
    val state: StateFlow<CartScreenState> = _state.asStateFlow()

    fun addToCart(productId: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.productId == productId }

        if (index >= 0) {
            current[index] = current[index].copy(quantity = current[index].quantity + 1)
        } else {
            current.add(CartProduct(productId, 1))
        }

        _cartItems.value = current
        updateState()
    }

    fun increaseQuantity(productId: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.productId == productId }
        if (index != -1) {
            current[index] = current[index].copy(quantity = current[index].quantity + 1)
            _cartItems.value = current
            updateState()
        }
    }

    fun decreaseQuantity(productId: Int) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.productId == productId }
        if (index != -1) {
            val qty = current[index].quantity
            if (qty > 1) {
                current[index] = current[index].copy(quantity = qty - 1)
            } else {
                current.removeAt(index)
            }
            _cartItems.value = current
            updateState()
        }
    }

    fun removeItem(productId: Int) {
        _cartItems.value = _cartItems.value.filter { it.productId != productId }
        updateState()
    }

    fun clearCart() {
        _cartItems.value = emptyList()
        updateState()
    }

    private fun updateState() {
        viewModelScope.launch {
            try {
                val allProducts = productDataSource.getAllProducts()
                val itemsUI = _cartItems.value.mapNotNull { cartItem ->
                    val product = allProducts.find { it.id == cartItem.productId }
                    product?.let {
                        CartItemUI(
                            productId = it.id,
                            title = it.title,
                            price = it.price,
                            image = it.image,
                            quantity = cartItem.quantity
                        )
                    }
                }

                val total = itemsUI.sumOf { it.price * it.quantity }

                _state.value = CartScreenState(
                    items = itemsUI,
                    total = total,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = CartScreenState(error = e.message ?: "Error desconocido")
            }
        }
    }
}
