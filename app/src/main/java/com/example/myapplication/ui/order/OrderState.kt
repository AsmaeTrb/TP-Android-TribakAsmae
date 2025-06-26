package com.example.myapplication.ui.order
import com.example.myapplication.data.Entities.Order

sealed class OrderState {
    object Idle : OrderState()
    object Loading : OrderState()
    data class Success(val order: Order) : OrderState()
    data class Error(val message: String) : OrderState()
}
