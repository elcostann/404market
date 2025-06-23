package ar.edu.uade.c012025.market404.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.uade.c012025.market404.R
import ar.edu.uade.c012025.market404.ui.Screens
import ar.edu.uade.c012025.market404.ui.theme.Primary

@Composable
fun LoginScreen(
    navController: NavController,
    onGoogleClick: () -> Unit,
    vm: LoginScreenViewModel = viewModel()
) { LaunchedEffect(Unit) {
    vm.uiEvent.collect {
        event ->
        navController.navigate(Screens.Home.route){
            popUpTo(Screens.Login.route){inclusive = true}
        }
    }

}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F0F0F), // Negro arriba
                        Primary, // Naranja
                        Color.White        // Blanco abajo
                    )
                )
            )){
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))

            Image(
                painter = painterResource(R.drawable.login),
                contentDescription = "404 Market Logo",
                modifier = Modifier
                    .width(450.dp)
                    .height(250.dp)
            )

            Spacer(Modifier.height(40.dp))

            Text("Bienvenido",   fontSize = 30.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(12.dp))
            Text("Iniciá sesión para continuar",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f))

            Spacer(Modifier.height(48.dp))

            Button(
                onClick = onGoogleClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_google_logo),
                    contentDescription = "Google logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text("Continuar con Google", color = Color.Black)
            }

            Spacer(Modifier.weight(1f))

            Text(
                "Al continuar aceptás los Términos y Condiciones.",
                fontSize = 12.sp,
                color = Color.Gray.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}
