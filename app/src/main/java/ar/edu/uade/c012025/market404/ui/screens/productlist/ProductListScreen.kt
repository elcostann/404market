package ar.edu.uade.c012025.market404.ui.screens.productlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.Data.Product
import ar.edu.uade.c012025.market404.R
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import ar.edu.uade.c012025.market404.ui.theme.Background
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.rememberAsyncImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel1(),
    navController: NavController
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredProducts by viewModel.filteredProducts.collectAsState()
    val products by viewModel.products.collectAsState()
    var isSearchMode by remember { mutableStateOf(false) }

    Column {
        MarketTopBar(
            title = "404 Market",
            isSearchMode = isSearchMode,
            searchQueryValue = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChanged(it) },
            onSearchClick = { isSearchMode = !isSearchMode },
            onFavoriteClick = { /* TODO */ },
            onCartClick = { navController.navigate("cart") },
            navController = navController
        )
        //Producto Random
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


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredProducts) { product ->
                ProductItem(product) {
                    navController.navigate("productDetail/${product.id}")
                }
            }
        }
        CategoryButtons(viewModel)
    }


}





@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(2.dp)
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





@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryButtons(viewModel: ProductListViewModel) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Línea superior delgada
        Divider(
            color = Color.Black.copy(alpha = 0.2f),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryButton("Todos","Todos" ,selectedCategory) { viewModel.getAllProducts() }
            CategoryButton("electronica", "electronics",selectedCategory) { viewModel.getProductsByCategory("electronics") }
            CategoryButton("joyeria", "jewelery",selectedCategory) { viewModel.getProductsByCategory("jewelery") }
            CategoryButton("ropa de hombre","men's clothing" ,selectedCategory) { viewModel.getProductsByCategory("men's clothing") }
            CategoryButton("ropa de mujer", "women's clothing",selectedCategory) { viewModel.getProductsByCategory("women's clothing") }
        }
    }
}

@Composable
fun CategoryButton(text: String,textI: String, selected: String, onClick: () -> Unit) {
    val isSelected = textI == selected
    val bgColor = if (isSelected) Color.Black else Primary
    val textColor = if (isSelected) Color.White else Color.Black

    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        modifier = Modifier.height(40.dp)
    ) {
        Text(text, fontSize = 14.sp, color = textColor)
    }
}

