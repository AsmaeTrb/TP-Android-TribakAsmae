package com.example.myapplication.data.Session
import android.content.Context
import com.example.myapplication.data.Entities.User
import com.example.myapplication.data.Entities.UserRole

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit().apply {
            putString("id", user.id)
            putString("email", user.email)
            putString("firstName", user.firstName)
            putString("lastName", user.lastName)
            putString("role", user.role.name)
            apply()
        }
    }

    fun getUser(): User? {
        val id = prefs.getString("id", null) ?: return null
        val email = prefs.getString("email", null) ?: return null
        val firstName = prefs.getString("firstName", "") ?: ""
        val lastName = prefs.getString("lastName", "") ?: ""
        val role = UserRole.valueOf(prefs.getString("role", "GUEST") ?: "GUEST")

        return User(id, email, "", firstName, lastName, false, role)
    }

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}
