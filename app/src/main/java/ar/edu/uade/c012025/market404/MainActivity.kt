package ar.edu.uade.c012025.market404

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import ar.edu.uade.c012025.market404.ui.NavigationStack
import ar.edu.uade.c012025.market404.ui.Screens
import ar.edu.uade.c012025.market404.ui.screens.carrito.CartViewModel
import ar.edu.uade.c012025.market404.ui.screens.favorito.FavoriteViewModel
import ar.edu.uade.c012025.market404.ui.theme.Market404Theme

class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: NavHostController

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        navController.navigate(Screens.Home.route) {
                            popUpTo(Screens.Login.route) { inclusive = true }
                        }
                    }
                }
        } catch (e: ApiException) {
            Log.e("AUTH", "Error en Google Sign-In", e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

        setContent {
            navController = rememberNavController()
            val cartViewModel: CartViewModel = viewModel()
            val favoriteViewModel: FavoriteViewModel = viewModel()

            Market404Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationStack(
                        onGoogleClick = {
                            googleSignInClient.signOut().addOnCompleteListener {
                                launcher.launch(googleSignInClient.signInIntent)
                            }
                        },
                        onLogoutClick = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate(Screens.Login.route) {
                                popUpTo(Screens.Home.route) { inclusive = true }
                            }
                        },
                        navController = navController,
                        cartViewModel = cartViewModel,
                        favoriteViewModel = favoriteViewModel
                    )
                }
            }
        }
    }
}
