
package com.example.myapplication.data.Entities

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val selectedSize: String = ""
)
