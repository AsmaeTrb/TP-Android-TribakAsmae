package com.example.myapplication.ui.product

data class LoginViewState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)