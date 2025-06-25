package ar.edu.uade.c012025.market404.ui.screens.carrito

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.ui.Screens
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.rememberAsyncImagePainter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    var showEmptyCartDialog = remember {mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        MarketTopBar(
            title = "404 Market",
            isSearchMode = false,
            iconsearch = false,
            searchQueryValue = "",
            onQueryChange = {},
            onSearchClick = {},
            onFavoriteClick = {navController.navigate(Screens.Favorite.route)},
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
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.Black)
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
                            Text(text = "us$${item.price}", color = Primary)
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
            onClick = {
                if (state.items.isEmpty()) {
                    showEmptyCartDialog.value = true
                } else {
                    viewModel.finalizarCompra()
                    navController.navigate(Screens.Success.route)
                }},
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text("Finalizar Compra", fontSize = 18.sp)
        }
        if (showEmptyCartDialog.value) {
            AlertDialog(
                onDismissRequest = { showEmptyCartDialog.value =false },
                title = { Text("Carrito vacío") },
                text = { Text("Agregá al menos un producto antes de finalizar la compra.") },
                confirmButton = {
                    Button(
                        onClick = { showEmptyCartDialog.value = false }
                    ) {
                        Text("Aceptar")
                    }

                }

            )
        }
    }
}
