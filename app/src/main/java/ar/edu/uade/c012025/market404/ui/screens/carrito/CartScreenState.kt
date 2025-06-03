package ar.edu.uade.c012025.market404.ui.screens.carrito


import ar.edu.uade.c012025.market404.Data.CartItemUI
import ar.edu.uade.c012025.market404.Data.CartProduct


data class CartScreenState(
    val items: List<CartItemUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val total: Double = 0.0
)
