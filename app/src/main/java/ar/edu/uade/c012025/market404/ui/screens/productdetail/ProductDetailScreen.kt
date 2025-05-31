@file:Suppress("UNREACHABLE_CODE")

package ar.edu.uade.c012025.market404.ui.screens.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import coil.compose.rememberAsyncImagePainter
import kotlin.math.roundToInt
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import ar.edu.uade.c012025.market404.ui.screens.productlist.ProductListViewModel
import ar.edu.uade.c012025.market404.ui.theme.Primary
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel = viewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    state.product?.let { product ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
            MarketTopBar(
                title = "",
                isSearchMode = false,
                searchQueryValue = "",
                onQueryChange = {},
                onSearchClick = {},
                onFavoriteClick = {},
                onCartClick = { navController.navigate("cart") },
                navController = navController,
                showBack = true
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
                    onClick = { /* TODO: Agregar al carrito */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("+ Agregar al Carrito")
                }

                Button(
                    onClick = { /* TODO: Agregar a favoritos */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("+ Favorito")
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





