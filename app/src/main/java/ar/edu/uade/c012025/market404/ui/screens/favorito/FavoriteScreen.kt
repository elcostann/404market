package ar.edu.uade.c012025.market404.ui.screens.favorito

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.ui.Screens
import ar.edu.uade.c012025.market404.ui.screens.carrito.CartViewModel
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.AsyncImage


@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        MarketTopBar(
            title = "",
            isSearchMode = false,
            iconsearch = false,
            searchQueryValue = "",
            onQueryChange = {},
            onSearchClick = {},
            onFavoriteClick = {},
            onCartClick = { navController.navigate(Screens.Cart.route) },
            navController = navController,
        )
        Text(
            text = "Mis Favoritos",
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp),
            color = Color.Black
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.error}")
                }
            }

            else -> {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.favorites) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable {
                                    navController.navigate("productDetail/${product.productId}")
                        },
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = product.image,
                                    contentDescription = product.title,
                                    modifier = Modifier.size(80.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = product.title)
                                    Text(text = "Us$${product.price}", color = Primary)
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(onClick = {
                                        viewModel.removeFromFavorites(product.productId)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }

                                    Button(
                                        onClick = {
                                            cartViewModel.addToCart(product.productId.toInt())
                                        },
                                        shape = RoundedCornerShape(50),
                                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                                    ) {

                                        Text("+")
                                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

