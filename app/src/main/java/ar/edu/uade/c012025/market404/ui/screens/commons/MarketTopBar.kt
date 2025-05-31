package ar.edu.uade.c012025.market404.ui.screens.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.uade.c012025.market404.ui.theme.Primary
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketTopBar(
    title: String,
    isSearchMode: Boolean,
    searchQueryValue: String,
    onSearchClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    onCartClick: () -> Unit,
    navController: NavController,
    showBack: Boolean = false
) {
    TopAppBar(
        title = {
            if (isSearchMode) {
                TextField(
                    value = searchQueryValue,
                    onValueChange = onQueryChange,
                    placeholder = { Text("Buscar...") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                IconButton(onClick = {
                    navController.navigate("productlist") {
                        popUpTo("productlist") { inclusive = true }
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.splash_logo),
                        contentDescription = "Logo 404 Market",


                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Filled.Search, contentDescription = "Buscar")
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
            }
            IconButton(onClick = onCartClick) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
            }
        }
    )
}
