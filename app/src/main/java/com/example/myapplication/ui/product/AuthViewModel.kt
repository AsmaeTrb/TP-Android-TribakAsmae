package com.example.myapplication.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Entities.User
import com.example.myapplication.data.Repository.UserRepository
import com.example.myapplication.data.Session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState: StateFlow<LoginViewState> = _loginState

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    val isLoggedIn: Boolean
        get() = _currentUser.value != null

    init {
        // ✅ Charger la session sauvegardée (si utilisateur déjà connecté)
        sessionManager.getUser()?.let {
            _currentUser.value = it
        }
    }

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
                    sessionManager.saveUser(user)
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
                    id = "", // <- temporaire
                    email = email,
                    password = password,
                    firstName = firstName,
                    lastName = lastName
                )
                val (registeredUser, errorMessage) = userRepository.registerUser(user)
                if (registeredUser != null) {
                    _currentUser.value = registeredUser
                    sessionManager.saveUser(registeredUser)
                    _loginState.value = LoginViewState(isSuccess = true)
                } else {
                    _loginState.value = LoginViewState(error = errorMessage)
                }
            } catch (e: Exception) {
                _loginState.value = LoginViewState(error = "Erreur d'inscription: ${e.message}")
            }
        }
    }
    fun loadUserSession() {
        val user = sessionManager.getUser()
        _currentUser.value = user
    }

    private fun logout() {
        _currentUser.value = null
        sessionManager.clearUser() // ✅ Effacer la session enregistrée
        _loginState.value = LoginViewState()
    }

    private fun resetState() {
        _loginState.value = LoginViewState()
    }
}
