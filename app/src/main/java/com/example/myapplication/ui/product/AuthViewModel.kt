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

    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState: StateFlow<LoginViewState> = _loginState

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Login -> login(intent.email, intent.password)
            is AuthIntent.Register -> register(intent.email, intent.password)
            AuthIntent.ResetState -> _loginState.value = LoginViewState()
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginViewState(isLoading = true)
            try {
                val user = userRepository.loginUser(email, password)
                if (user != null) {
                    _loginState.value = LoginViewState(isSuccess = true)
                } else {
                    _loginState.value = LoginViewState(error = "Identifiants incorrects")
                }
            } catch (e: Exception) {
                _loginState.value = LoginViewState(error = "Erreur : ${e.message}")
            }
        }
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginViewState(isLoading = true)
            try {
                val (success, errorMessage) = userRepository.registerUser(
                    User(id = "", email = email, password = password)
                )
                if (success) {
                    _loginState.value = LoginViewState(isSuccess = true)
                } else {
                    _loginState.value = LoginViewState(error = errorMessage)
                }
            } catch (e: Exception) {
                _loginState.value = LoginViewState(error = "Erreur : ${e.message}")
            }
        }
    }

}