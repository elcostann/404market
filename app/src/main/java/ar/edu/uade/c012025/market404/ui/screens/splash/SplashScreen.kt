package ar.edu.uade.c012025.market404.ui.screens.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.runtime.Composable
import ar.edu.uade.c012025.market404.R
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ar.edu.uade.c012025.market404.ui.theme.Primary


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2500) // Espera 2.5 segundos
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true } // elimina splash del backstack
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F0F0F), // Negro arriba
                        Primary, // Naranja (tu color primario)
                        Color.White        // Blanco abajo
                    )
                )
            ),

                contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Splash 404 Market",
            modifier = Modifier.fillMaxSize()
        )
    }

}
