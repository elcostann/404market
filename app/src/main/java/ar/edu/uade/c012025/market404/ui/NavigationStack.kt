package ar.edu.uade.c012025.market404.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.uade.c012025.market404.ui.screens.login.LoginScreen
import ar.edu.uade.c012025.market404.ui.screens.productdetail.ProductDetailScreen
import ar.edu.uade.c012025.market404.ui.screens.productlist.ProductListScreen
import ar.edu.uade.c012025.market404.ui.screens.splash.SplashScreen

/**
 * @param onGoogleClick    callback que disparás desde el LoginScreen
 * @param onLogoutClick    callback (por si querés mostrar un botón de logout en Home)
 * @param navController    valor por defecto para no tener que pasarlo desde MainActivity
 */
@Composable
fun NavigationStack(
    onGoogleClick: () -> Unit,
    onLogoutClick: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController   = navController,
        startDestination = Screens.Splash.route
    ) {
        // 1) Pantalla de splash
        composable(Screens.Splash.route) {
            SplashScreen(navController)
        }

        // 2) Login con Google
        composable(Screens.Login.route) {
            LoginScreen(
                navController  = navController,
                onGoogleClick  = onGoogleClick
            )
        }

        // 3) Listado principal (Home)
        composable(Screens.Home.route) {
            ProductListScreen(
                navController  = navController,
            )
        }

        // 4) Detalle de producto, con parámetro productId
        composable(Screens.ProductDetail.route) { backStackEntry ->
            val id = backStackEntry
                .arguments
                ?.getString("productId")
                ?.toIntOrNull()
                ?: 0

            ProductDetailScreen(
                productId     = id,
                navController = navController
            )
        }
    }
}
