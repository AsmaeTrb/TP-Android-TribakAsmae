package com.example.myapplication.data.Entities
import com.google.gson.annotations.SerializedName
data class Order(
    @SerializedName("userId") val userId: String,
    @SerializedName("items") val items: List<CartItem>,
    @SerializedName("totalPrice") val total: Double,
    @SerializedName("shippingAddress") val shippingAddress: Address
)

data class Address(
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String,
    @SerializedName("zipCode") val zipCode: String,
    @SerializedName("street") val street: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("fullName") val fullName: String
)

