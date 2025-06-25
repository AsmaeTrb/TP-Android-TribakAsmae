package com.example.myapplication.ui.cart

import com.example.myapplication.data.Entities.Product
sealed class CartIntent {
    data class AddToCart(val product: Product, val selectedSize: String) : CartIntent()
    data class RemoveFromCart(val product: Product) : CartIntent()
    data class ChangeQuantity(val product: Product, val quantity: Int) : CartIntent()
}

