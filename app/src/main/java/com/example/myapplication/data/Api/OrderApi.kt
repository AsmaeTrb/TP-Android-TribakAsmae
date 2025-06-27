package com.example.myapplication.data.Api

import com.example.myapplication.data.Entities.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @POST("orders")
    suspend fun submitOrder(@Body order: Order): Order

    @GET("orders")
    suspend fun getOrders(): List<Order>

}
