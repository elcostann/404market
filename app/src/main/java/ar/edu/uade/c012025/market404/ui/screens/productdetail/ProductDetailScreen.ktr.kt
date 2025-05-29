package ar.edu.uade.c012025.market404.ui.screens.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import ar.edu.uade.c012025.market404.R
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductDetailScreen(productId: Int, navController: NavController? = null) {
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(productId) {
        try {
            product = ProductApiDataSource().getProductById(productId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    product?.let {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Header
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
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("404 Market", fontWeight = FontWeight.Bold)
                }
                Row {
                    IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = null) }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFFBBF24))
                    }
                    IconButton(onClick = { }) { Icon(Icons.Default.ShoppingCart, contentDescription = null) }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen
            Image(
                painter = rememberAsyncImagePainter(it.image),
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título y precio
            Text(text = it.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$${it.price}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF97316))
            Spacer(modifier = Modifier.height(8.dp))

            // Estrellas + reviews
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(4) {
                    Text("★", fontSize = 20.sp, color = Color(0xFFFFC107))
                }
                Text("☆", fontSize = 20.sp, color = Color(0xFF212121))
                Spacer(modifier = Modifier.width(8.dp))
                Text("120 reviews", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Text(
                text = it.description,
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("+  Agregar al Carrito")
                }
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("+  Agregar a Favorito")
                }
            }

            Spacer(modifier = Modifier.height(32.dp)) // margen inferior
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}



