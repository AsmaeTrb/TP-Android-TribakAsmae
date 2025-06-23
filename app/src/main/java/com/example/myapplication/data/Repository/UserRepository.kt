package com.example.myapplication.data.Repository
import com.example.myapplication.data.Api.UserApi
import com.example.myapplication.data.Entities.LoginRequest
import com.example.myapplication.data.Entities.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun getAllUsers(): List<User> {
        return userApi.getUsers()
    }
    suspend fun registerUser(user: User): Pair<Boolean, String?> {
        val response = userApi.registerUser(user)
        return if (response.isSuccessful) {
            Pair(true, null)
        } else {
            val errorMessage = response.errorBody()?.string()
            Pair(false, errorMessage ?: "Échec de l'inscription")
        }
    }


    suspend fun loginUser(email: String, password: String): User? {
        val response = userApi.loginUser(LoginRequest(email, password))
        return if (response.isSuccessful) response.body() else null
    }
}
