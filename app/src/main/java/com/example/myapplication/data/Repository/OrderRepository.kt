package com.example.myapplication.data.Repository
import com.example.myapplication.data.Api.OrderApi
import com.example.myapplication.data.Entities.Order
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderApi: OrderApi
) {
    suspend fun placeOrder(order: Order): Order {
        return orderApi.submitOrder(order)
    }
}