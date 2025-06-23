package com.example.myapplication.nav

import com.example.myapplication.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val iconRes: Int
) {
    object Home : BottomNavItem("home", "HOME", R.drawable.home)
    object Products : BottomNavItem("products", "PRODUCTS", R.drawable.category)
    object Wishlist : BottomNavItem("wishlist", "WISHLIST", R.drawable.stars)
    object Profile : BottomNavItem("profile", "PROFILE", R.drawable.account)

    companion object {
        val items = listOf(Home, Products, Wishlist, Profile)
    }
}
