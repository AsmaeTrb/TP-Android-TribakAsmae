package com.example.myapplication.ui.product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Entities.User
import com.example.myapplication.data.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    // État actuel
    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState: StateFlow<LoginViewState> = _loginState

    // Utilisateur connecté
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    // Vérifie si l'utilisateur est connecté
    val isLoggedIn: Boolean
        get() = _currentUser.value != null

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Login -> login(intent.email, intent.password)
            is AuthIntent.Register -> register(
                intent.email,
                intent.password,
                intent.firstName,
                intent.lastName
            )
            AuthIntent.Logout -> logout()
            AuthIntent.ResetState -> resetState()
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginViewState(isLoading = true)
            try {
                val user = userRepository.loginUser(email, password)
                if (user != null) {
                    _currentUser.value = user
                    _loginState.value = LoginViewState(isSuccess = true)
                } else {
                    _loginState.value = LoginViewState(error = "Identifiants incorrects")
                }
            } catch (e: Exception) {
                _loginState.value = LoginViewState(error = "Erreur: ${e.message}")
            }
        }
    }

    private fun register(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _loginState.value = LoginViewState(isLoading = true)
            try {
                val user = User(
                    id = "",
                    email = email,
                    password = password,
                    firstName = firstName,
                    lastName = lastName
                )
                val (success, errorMessage) = userRepository.registerUser(user)
                if (success) {
                    _currentUser.value = user
                    _loginState.value = LoginViewState(isSuccess = true)
                } else {
                    _loginState.value = LoginViewState(error = errorMessage)
                }
            } catch (e: Exception) {
                _loginState.value = LoginViewState(error = "Erreur d'inscription: ${e.message}")
            }
        }
    }


    private fun logout() {
        _currentUser.value = null
        _loginState.value = LoginViewState()
    }

    private fun resetState() {
        _loginState.value = LoginViewState()
    }
}