package com.example.myapplication.data.Entities
import com.google.gson.annotations.SerializedName
data class Order(
    @SerializedName("userId") val userId: String,
    @SerializedName("items") val items: List<CartItem>,
    @SerializedName("totalPrice") val total: Double,
    @SerializedName("shippingAddress") val shippingAddress: Address,
    @SerializedName("status") val status: String? = "pending",
    @SerializedName("email") val email: String        // 👈 AJOUTER CETTE LIGNE

)
 data class Address(
@SerializedName("fullName") val fullName: String,
@SerializedName("address") val address: String,          // au lieu de "street"
@SerializedName("postalCode") val postalCode: String,    // au lieu de "zipCode"
@SerializedName("phone") val phone: String,              // au lieu de "phoneNumber"
@SerializedName("city") val city: String,
@SerializedName("country") val country: String,
     @SerializedName("email") val email: String // 👈 ce champ est obligatoire maintenant

 )



