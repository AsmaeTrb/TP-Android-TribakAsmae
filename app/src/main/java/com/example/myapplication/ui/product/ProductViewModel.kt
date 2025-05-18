package com.example.myapplication.ui.product
import com.example.myapplication.model.Product

data class ProductViewState(
    val products: List<Product> = emptyList(),
    val selectedProductId: String? = null,
    val isLoading: Boolean = false
)
