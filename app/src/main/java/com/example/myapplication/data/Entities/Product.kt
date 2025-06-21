package com.example.myapplication.data.Entities
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName("nameProduct")
    val nameProduct: String,
    val brand: String,
    val price: Double,
    val description: String,
    val image: String,
    val ingredients: List<String>,
    val howToUse: String,
    val quantity: Int
)