package com.example.myapplication.data.Entities

enum class PaymentMethod {
    CARD, PAYPAL
}

data class CardDetails(
    val holder: String = "",
    val number: String = "",
    val expiry: String = "",
    val cvv: String = ""
)
