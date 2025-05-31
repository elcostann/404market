package ar.edu.uade.c012025.market404.ui.screens.productlist


import ar.edu.uade.c012025.market404.Data.Product

data class ProductListScreenState(
    val allProducts: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "Todos"
)

