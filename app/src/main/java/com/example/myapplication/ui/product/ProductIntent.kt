package com.example.myapplication.ui.product

// ui/product/ProductIntent.kt

sealed class ProductIntent {
    data class ShowProductDetails(val productId: String) : ProductIntent()
    object LoadProducts : ProductIntent()
}
