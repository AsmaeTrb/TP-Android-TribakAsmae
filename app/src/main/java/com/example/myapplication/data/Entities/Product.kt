package com.example.myapplication.data.Entities
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("category") val category: String,
    @SerializedName("color") val color: String? = null,
    @SerializedName("origin") val origin: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("sizes") val sizes: List<ProductSize>
)
data class ProductSize(
    @SerializedName("size") val size: String,
    @SerializedName("quantity") val quantity: Int
)