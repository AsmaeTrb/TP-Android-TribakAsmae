package com.example.myapplication.data.Api

import com.example.myapplication.data.Entities.User
import retrofit2.http.GET

interface UserApi {
    @GET("users.json")
    suspend fun getUsers(): List<User>
}
