package com.example.myapplication.data.Api
import com.example.myapplication.data.Entities.Product
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<Product>
}