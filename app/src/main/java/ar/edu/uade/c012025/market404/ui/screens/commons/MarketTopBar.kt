package ar.edu.uade.c012025.market404.ui.screens.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketTopBar(
    title: String,
    isSearchMode: Boolean,
    iconsearch: Boolean = true,
    searchQueryValue: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onCartClick: () -> Unit,
    navController: NavController,
    showBack: Boolean = false,
    userName: String? = null,
    onLogoutClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())


    ) {
        if (userName != null && onLogoutClick != null) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hola, $userName 👋",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Button(
                        onClick = onLogoutClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Logout", color = Color.White, fontSize = 12.sp)
                    }

                }
            }
        }


            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets(0)
                    ,
                title = {
                    if (isSearchMode) {
                        TextField(
                            value = searchQueryValue,
                            onValueChange = onQueryChange,
                            placeholder = { Text("Buscar...") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        IconButton(onClick = {
                            navController.navigate("productlist") {
                                popUpTo("productlist") { inclusive = true }
                            }
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.splash_logo),
                                contentDescription = "Logo 404 Market"
                            )
                        }
                    }
                },
                actions = {
                    if (iconsearch) {
                        IconButton(onClick = onSearchClick) {
                            Icon(Icons.Filled.Search, contentDescription = "Buscar")
                        }
                    }
                    IconButton(onClick = onFavoriteClick) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
                    }
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                    }
                },
                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                        )

            )

        }
    }


