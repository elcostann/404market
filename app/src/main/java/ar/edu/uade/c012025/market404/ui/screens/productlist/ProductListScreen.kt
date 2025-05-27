package ar.edu.uade.c012025.market404.ui.screens.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel(),
    navController: NavController
) {
    val products by viewModel.products.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        // 🛒 Header
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Primary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("404 Market", fontWeight = FontWeight.Bold)
                }
            },
            actions = {
                IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = "Buscar") }
                IconButton(onClick = {}) { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") }
            }
        )

        // 🧡 Producto Random
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Producto Random", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
                Button(onClick = {
                    val randomId = products.randomOrNull()?.id ?: 1
                    navController.navigate("productDetail/$randomId")
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Ver", color = Color.White)
                }
            }
        }

        // 🏷️ Título
        Text(
            "Productos Destacados",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        // 🧱 Grilla
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(products) { product ->
                ProductItem(product) {
                    navController.navigate("productDetail/${product.id}")
                }
            }
        }

        // 🎯 Filtros
        CategoryButtons()
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$${product.price}",
                color = Primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CategoryButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5E7EB))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            CategoryButton("Electronica")
            CategoryButton("Joyeria")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            CategoryButton("Ropa Hombre")
            CategoryButton("Ropa Mujer")
        }
    }
}

@Composable
fun CategoryButton(text: String) {
    Button(
        onClick = { /* filtrado futuro */ },
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Text(text)
    }
}
