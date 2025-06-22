package com.example.myapplication.data.Repository
import com.example.myapplication.data.Api.UserApi
import com.example.myapplication.data.Entities.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun getAllUsers(): List<User> {
        return userApi.getUsers()
    }
}
