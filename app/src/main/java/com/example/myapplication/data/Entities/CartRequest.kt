package com.example.myapplication.data.Entities

data class CartRequest(
    val userId: String,
    val items: List<Item>
) {
    data class Item(
        val productId: String,
        val quantity: Int,
        val selectedSize: String
    )
}
