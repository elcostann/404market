package ar.edu.uade.c012025.market404.ui.screens.favorito

import ar.edu.uade.c012025.market404.Data.FavoriteProduct

data class FavoriteScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val favorites: List<FavoriteProduct> = emptyList()
)