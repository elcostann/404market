package ar.edu.uade.c012025.market404.ui.screens.productdetail



import ar.edu.uade.c012025.market404.Data.Product

data class ProductDetailScreenState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
