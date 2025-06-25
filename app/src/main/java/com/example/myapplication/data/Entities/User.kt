package com.example.myapplication.data.Entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("subscribeToNewsletter") val subscribeToNewsletter: Boolean = false,
    @SerializedName("role") val role: UserRole = UserRole.GUEST
)
