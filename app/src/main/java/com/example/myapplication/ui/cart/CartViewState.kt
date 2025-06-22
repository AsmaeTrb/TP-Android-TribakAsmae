package com.example.myapplication.ui.cart

import com.example.myapplication.data.Entities.CartItem

data class CartViewState(
    val items: List<CartItem> = emptyList()
)