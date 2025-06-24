package com.example.myapplication.ui.generateCategoryList

import com.example.myapplication.data.Entities.Product

fun generateCategoryList(products: List<Product>): List<Pair<String, String>> {
    val mainCategories = listOf(
        "bags",
        "jakects",
        "dresses",
        "COSTUME JEWELRY",
        "SMALL LEATHER GOODS"
    )

    return mainCategories.map { category ->
        val firstProduct = products.firstOrNull { it.category == category }
        category to (firstProduct?.images?.firstOrNull() ?: "https://default-image.com/placeholder.jpg")
    }
}