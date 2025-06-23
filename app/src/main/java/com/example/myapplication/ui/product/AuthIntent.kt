package com.example.myapplication.ui.product

sealed class AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent()
    data class Register(val email: String, val password: String) : AuthIntent()
    object ResetState : AuthIntent()
}