package ar.edu.uade.c012025.market404.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.uade.c012025.market404.ui.screens.productlist.ProductListScreen
import ar.edu.uade.c012025.market404.ui.screens.productdetail.ProductDetailScreen
import ar.edu.uade.c012025.market404.ui.screens.splash.SplashScreen

@Composable
fun NavigationStack(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("home") {
            ProductListScreen(navController = navController)
        }

        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            productId?.let {
                ProductDetailScreen(productId = it)
            }
        }
    }
}
