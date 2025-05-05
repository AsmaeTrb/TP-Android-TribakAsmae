package com.example.myapplication.model
data class Product(
    val id: String,
    val nameProduct: String,
    val brand: String,
    val price: Double,
    val description: String,
    val image: String,
    val ingredients: List<String>,
    val howToUse: String,
    val quantity: Int
)
