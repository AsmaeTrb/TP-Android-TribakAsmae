package com.example.myapplication.data.Entities
import com.google.gson.annotations.SerializedName
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: UserRole = UserRole.GUEST
)
