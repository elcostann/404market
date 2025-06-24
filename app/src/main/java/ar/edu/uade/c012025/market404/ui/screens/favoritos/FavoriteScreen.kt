package ar.edu.uade.c012025.market404.ui.screens.favoritos

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.Data.FavoriteRepository
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import coil.compose.AsyncImage

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteRepository: FavoriteRepository,
    productRepository: ProductApiDataSource
) {
    val viewModel: FavoriteViewModel = viewModel(
        factory = FavoriteViewModelFactory(favoriteRepository, productRepository)
    )

    val productos = viewModel.favoriteProducts




    Column {
        MarketTopBar(
            title = "Favoritos",
            isSearchMode = false,
            iconsearch = false,
            searchQueryValue = "",
            onQueryChange = {},
            onSearchClick = {},
            onFavoriteClick = {},
            onCartClick = { navController.navigate("cart") },
            navController = navController
        )

        if (productos.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tenés productos favoritos")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(productos) { product ->
                    ProductCard(product = product) {
                        navController.navigate("productDetail/${product.id}")
                    }
                }
            }
        }
    }
}
@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(product.title, fontWeight = FontWeight.Bold)
                Text("$${product.price}", color = Color.Gray)
            }
        }
    }
}




