package com.example.myapplication.ui.generateCategoryList

import com.example.myapplication.data.Entities.Product

fun generateCategoryList(products: List<Product>): List<Pair<String, String>> {
    val mainCategories = listOf(
        "bags",
        "glasses",
        "jewelry",
        "jackets",
        "dresses",
        "shoes"
    )

    // 🔁 Construire la liste avec une image personnalisée pour chaque catégorie
    return mainCategories.map { category ->
        val imageUrl = "http://192.168.87.107:3000/assets/${category.lowercase()}.png"

        category to imageUrl
    }
}
