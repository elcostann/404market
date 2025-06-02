package ar.edu.uade.c012025.market404.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.uade.c012025.market404.ui.screens.carrito.CartScreen
import ar.edu.uade.c012025.market404.ui.screens.carrito.CartViewModel
import ar.edu.uade.c012025.market404.ui.screens.login.LoginScreen
import ar.edu.uade.c012025.market404.ui.screens.productdetail.ProductDetailScreen
import ar.edu.uade.c012025.market404.ui.screens.productlist.ProductListScreen
import ar.edu.uade.c012025.market404.ui.screens.splash.SplashScreen


@Composable
fun NavigationStack(
    onGoogleClick: () -> Unit,
    onLogoutClick: () -> Unit,
    navController: NavHostController = rememberNavController(),
    cartViewModel: CartViewModel // 💡 Parámetro corregido (con minúscula)
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        composable(Screens.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screens.Login.route) {
            LoginScreen(
                navController = navController,
                onGoogleClick = onGoogleClick
            )
        }

        composable(Screens.Home.route) {
            ProductListScreen(
                navController = navController,
                cartViewModel = cartViewModel // 🛒 Pasamos el viewModel
            )
        }

        composable(Screens.ProductDetail.route) { backStackEntry ->
            val id = backStackEntry
                .arguments
                ?.getString("productId")
                ?.toIntOrNull()
                ?: 0

            ProductDetailScreen(
                productId = id,
                navController = navController,
                cartViewModel = cartViewModel // 🛒 Pasamos el viewModel
            )
        }

        composable(Screens.Cart.route) {
            CartScreen(
                navController = navController,
                viewModel = cartViewModel // 🛒 Usamos el mismo viewModel
            )
        }
    }
}
