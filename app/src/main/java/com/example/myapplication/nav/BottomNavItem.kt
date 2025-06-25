package com.example.myapplication.nav

import com.example.myapplication.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val iconRes: Int
) {
    object Home : BottomNavItem("home", "HOME", R.drawable.home)
    object Cart : BottomNavItem("cart", "PANIER", R.drawable.stars)
    object Profile : BottomNavItem("profile", "PROFILE", R.drawable.account)
    object Products : BottomNavItem("catalogue", "PRODUCTS", R.drawable.category)
    object AuthWelcome : BottomNavItem("authWelcome", "PROFILE", R.drawable.account)

    companion object {
        val items = listOf(Home, Products, Cart, Profile)
    }
}