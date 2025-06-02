package ar.edu.uade.c012025.market404.ui.screens.carrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.rememberAsyncImagePainter

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        MarketTopBar(
            title = "404 Market",
            isSearchMode = false,
            iconsearch = false,
            searchQueryValue = "",
            onQueryChange = {},
            onSearchClick = {},
            onFavoriteClick = {},
            onCartClick = {},
            navController = navController,
        )

        Text(
            text = "Mi Carrito",
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp),
            color = Color.Black
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.items) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = item.image),
                            contentDescription = item.title,
                            modifier = Modifier.size(80.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.title)
                            Text(text = "$${item.price}", color = Primary)
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { viewModel.decreaseQuantity(item.productId) }) {
                                    Text("-")
                                }
                                Text(
                                    text = item.quantity.toString(),
                                    modifier = Modifier.padding(8.dp)
                                )
                                IconButton(onClick = { viewModel.increaseQuantity(item.productId) }) {
                                    Text("+")
                                }
                            }
                        }

                        IconButton(onClick = { viewModel.removeItem(item.productId) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }

        Text(
            text = "TOTAL: us$${"%.2f".format(state.total)}",
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            fontSize = 20.sp
        )

        Button(
            onClick = { /* Finalizar compra */ },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text("Finalizar Compra", fontSize = 18.sp)
        }
    }

}