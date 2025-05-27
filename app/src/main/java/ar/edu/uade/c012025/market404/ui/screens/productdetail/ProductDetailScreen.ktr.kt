package ar.edu.uade.c012025.market404.ui.screens.productdetail



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.Data.ProductApiDataSource
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProductDetailScreen(productId: Int) {
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(productId) {
        try {
            product = ProductApiDataSource().getProductById(productId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    product?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(it.image),
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$${it.price}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description)
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
