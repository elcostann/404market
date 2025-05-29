// LoginScreenViewModel.kt
package ar.edu.uade.c012025.market404.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Emite "loginOK" cuando detecta que ya hay un user logueado
 * o cuando llamamos a onLoginSuccess().
 */
class LoginScreenViewModel : ViewModel() {

    private val _uiEvent = Channel<String>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkAuthStatus()
    }

    /** Si ya hay user en FirebaseAuth, avisamos al UI. */
    private fun checkAuthStatus() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModelScope.launch { _uiEvent.send("loginOK") }
        }
    }

    /** Llamar cuando el sign-in con credenciales termine OK. */
    fun onLoginSuccess() {
        viewModelScope.launch { _uiEvent.send("loginOK") }
    }
}
