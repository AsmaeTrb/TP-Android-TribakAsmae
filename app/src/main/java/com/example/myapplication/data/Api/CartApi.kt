package com.example.myapplication.data.Api
import com.example.myapplication.data.Entities.CartRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.myapplication.data.Entities.CartRequest.Item

interface CartApi {
    @GET("cart/{userId}")
    suspend fun getCart(@Path("userId") userId: String): List<Item>

    @POST("cart")
    suspend fun saveCart(@Body request: CartRequest)
}
