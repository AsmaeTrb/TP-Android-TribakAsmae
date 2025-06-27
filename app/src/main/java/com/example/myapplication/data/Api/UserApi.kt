package com.example.myapplication.data.Api

import com.example.myapplication.data.Entities.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import com.example.myapplication.data.Entities.LoginRequest

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<User>
    @POST("/register")
    suspend fun registerUser(@Body user: User): Response<User>

    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<User>
}
