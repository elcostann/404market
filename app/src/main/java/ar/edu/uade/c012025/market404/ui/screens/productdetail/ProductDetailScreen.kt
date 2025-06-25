@file:Suppress("UNREACHABLE_CODE")

package ar.edu.uade.c012025.market404.ui.screens.productdetail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.Data.FavoriteProduct
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.ui.Screens
import ar.edu.uade.c012025.market404.ui.screens.carrito.CartViewModel
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import ar.edu.uade.c012025.market404.ui.screens.favorito.FavoriteViewModel
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.rememberAsyncImagePainter
import kotlin.math.roundToInt

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel = viewModel(),
    navController: NavController,
    cartViewModel: CartViewModel,
    favoriteViewModel: FavoriteViewModel
) {

    val state by viewModel.state.collectAsState()
    val fav by favoriteViewModel.favorites.collectAsState()

    LaunchedEffect(productId) {
        favoriteViewModel.loadFavorites()
        viewModel.loadProduct(productId)
    }

    state.product?.let { product ->
        val isFavorite by remember(fav, product.id) {
            derivedStateOf {
                fav.any { it.productId == product.id.toString() }
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
            MarketTopBar(
                title = "",
                isSearchMode = false,
                iconsearch = false,
                searchQueryValue = "",
                onQueryChange = {},
                onSearchClick = {},
                onFavoriteClick = {navController.navigate(Screens.Favorite.route)},
                onCartClick = { navController.navigate(Screens.Cart.route)},
                navController = navController,
            )

            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))

            Text(product.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("us$${product.price}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)

            Spacer(Modifier.height(12.dp))

            RatingSection(product)

            Spacer(Modifier.height(16.dp))

            Text(product.description, fontSize = 16.sp, color = Color.DarkGray)

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { cartViewModel.addToCart(productId) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("+")
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                }



                Log.d("FavTest","isFavorite= $isFavorite")

                Button(
                    onClick = {


                        if (isFavorite) {
                            Log.d("FavTest","entro al if")

                            favoriteViewModel.removeFromFavorites(product.id.toString())

                        } else {
                            Log.d("FavTest","entro al else")

                            favoriteViewModel.addToFavorites(
                                FavoriteProduct(
                                    productId = product.id.toString(),
                                    title = product.title,
                                    image = product.image,
                                    price = product.price
                                )

                            )

                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)

                ) {if (isFavorite){
                    Text("-")
                    Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                }
                    else{
                    Text("+")
                    Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                    }

                }
            }
        }

    } ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun RatingSection(product: Product) {
    val fullStars = product.rating.rate.roundToInt()
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(fullStars) { Text("★", fontSize = 18.sp, color = Color(0xFFFFC107)) }
        if (product.rating.rate - fullStars >= 0.5) Text("⯪", fontSize = 18.sp, color = Color(0xFFFFC107))
        val empty = 5 - fullStars - if (product.rating.rate - fullStars >= 0.5) 1 else 0
        repeat(empty) { Text("☆", fontSize = 18.sp, color = Color(0xFFB0BEC5)) }

        Spacer(Modifier.width(8.dp))
        Text("(${product.rating.count} reviews)", style = MaterialTheme.typography.bodySmall)
    }
}





