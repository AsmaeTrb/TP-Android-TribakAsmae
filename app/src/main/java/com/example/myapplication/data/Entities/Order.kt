package com.example.myapplication.data.Entities

data class Order(
    val userId: String,
    val products: List<CartItem>, // Crée aussi la classe CartItem si ce n’est pas fait
    val total: Double,
    val shippingAddress: Address
)

data class Address(
    val country: String,
    val city: String,
    val zipCode: String,
    val street: String,
    val phoneNumber: String,
    val fullName: String
)
