package com.example.myapplication.ui.order
import com.example.myapplication.data.Entities.Order

sealed class OrderIntent {
    data class PlaceOrder(val order: Order) : OrderIntent()
}