package com.example.myapplication.ui.cart

import com.example.myapplication.data.Entities.Product

data class CartItem(
    val product: Product,
    val quantity: Int = 1
)

data class CartViewState(
    val items: List<CartItem> = emptyList()
)
