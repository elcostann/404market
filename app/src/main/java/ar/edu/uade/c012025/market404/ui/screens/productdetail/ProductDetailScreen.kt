package ar.edu.uade.c012025.market404.ui.screens.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import ar.edu.uade.c012025.market404.ui.theme.Primary

@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavController? = null
) {
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(productId) {
        try {
            product = ProductApiDataSource().getProductById(productId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    product?.let { it ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = Primary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("404 Market", fontWeight = FontWeight.Bold)
                }
                Row {
                    IconButton(onClick = { /* buscar */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = { /* favorito */ }) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorito",
                            tint = Color(0xFFFBBF24)
                        )
                    }
                    IconButton(onClick = { /* carrito */ }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // imagen
            Image(
                painter = rememberAsyncImagePainter(it.image),
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))

            // titulo y precio
            Text(it.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                text = "$${it.price}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Primary
            )

            Spacer(Modifier.height(12.dp))

            // Rating dinámico
            Row(verticalAlignment = Alignment.CenterVertically) {
                val fullStars = it.rating.rate.roundToInt()
                repeat(fullStars) {
                    Text("★", fontSize = 18.sp, color = Color(0xFFFFC107))
                }
                // si hay .5 o más, mostramos media estrella
                if (it.rating.rate - fullStars >= 0.5) {
                    Text("⯪", fontSize = 18.sp, color = Color(0xFFFFC107)) // Unicode de media estrella
                }
                val emptyStars = 5 - fullStars - if (it.rating.rate - fullStars >= 0.5) 1 else 0
                repeat(emptyStars) {
                    Text("☆", fontSize = 18.sp, color = Color(0xFFB0BEC5))
                }
                Spacer(Modifier.width(8.dp))
                Text("(${it.rating.count} reviews)", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(16.dp))

            // Descripción
            Text(
                text = it.description,
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(24.dp))

            // botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { /* agregar al carrito */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("+  Agregar al Carrito")
                }
                Button(
                    onClick = { /* agregar a favorito */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("+  Agregar a Favorito")
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    } ?: run {
        // carga
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

