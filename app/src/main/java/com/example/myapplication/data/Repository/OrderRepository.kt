package com.example.myapplication.data.Repository
import com.example.myapplication.data.Api.OrderApi
import com.example.myapplication.data.Entities.Order
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderApi: OrderApi
) {
    suspend fun placeOrder(order: Order): Order {
        return orderApi.submitOrder(order)
    }
    suspend fun getOrders(userId: String?): List<Order> {
        return orderApi.getOrdersByUserId(userId)
    }
}