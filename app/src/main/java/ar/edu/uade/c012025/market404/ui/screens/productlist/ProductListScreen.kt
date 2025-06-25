package ar.edu.uade.c012025.market404.ui.screens.productlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ar.edu.uade.c012025.market404.ui.Screens
import ar.edu.uade.c012025.market404.ui.screens.carrito.CartViewModel
import ar.edu.uade.c012025.market404.ui.screens.commons.MarketTopBar
import ar.edu.uade.c012025.market404.ui.theme.Background
import ar.edu.uade.c012025.market404.ui.theme.Primary
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel1(),
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery = uiState.searchQuery
    val filteredProducts = uiState.filteredProducts
    val products = uiState.allProducts
    val selectedCategory = uiState.selectedCategory
    var isSearchMode by remember { mutableStateOf(false) }

    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "Usuario"

    Column {
        MarketTopBar(
            title = "404 Market",
            isSearchMode = isSearchMode,
            iconsearch = true,
            searchQueryValue = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChanged(it) },
            onSearchClick = { isSearchMode = !isSearchMode },
            onFavoriteClick = { navController.navigate(Screens.Favorite.route) },
            onCartClick = { navController.navigate(Screens.Cart.route) },
            navController = navController,
            userName = userName,
            onLogoutClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(Screens.Login.route) {
                    popUpTo(0)
                }
            }
        )

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
                    Image(
                        painter = painterResource(id = R.drawable.randomproduct),
                        contentDescription = "random 404 Market",
                        modifier = Modifier.width(70.dp)
                    )
                    Text(
                        "Producto Random",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Button(
                        onClick = {
                            val randomId = products.randomOrNull()?.id ?: 1
                            navController.navigate("productDetail/$randomId")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Ver", color = Color.White)
                    }
                }
            }
            if (isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredProducts) { product ->
                    ProductItem(
                        product = product,
                        onClick = { navController.navigate("productDetail/${product.id}") },
                        onAddToCart = { cartViewModel.addToCart(product.id) }
                    )
                }
            }

                CategoryButtons(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    if (category == "Todos") viewModel.getAllProducts()
                    else viewModel.getProductsByCategory(category)
                }
            )
        }
    }
}








@Composable
fun ProductItem(product: Product, onClick: () -> Unit, onAddToCart: () -> Unit) {
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
                text = "Us$${product.price}",
                color = Primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = Primary)

            ) { Text("+")
            Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
            }

        }
    }
}





@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryButtons(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            CategoryButton("Todos", "Todos", selectedCategory) { onCategorySelected("Todos") }
            CategoryButton("electronica", "electronics", selectedCategory) { onCategorySelected("electronics") }
            CategoryButton("joyeria", "jewelery", selectedCategory) { onCategorySelected("jewelery") }
            CategoryButton("ropa de hombre", "men's clothing", selectedCategory) { onCategorySelected("men's clothing") }
            CategoryButton("ropa de mujer", "women's clothing", selectedCategory) { onCategorySelected("women's clothing") }
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

