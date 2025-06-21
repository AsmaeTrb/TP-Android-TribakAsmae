package com.example.myapplication.ui.product

sealed class ProductIntent {
    object LoadProducts : ProductIntent()
    data class ShowProductDetails(val productId: String) : ProductIntent()
}
