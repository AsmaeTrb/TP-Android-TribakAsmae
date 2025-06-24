package com.example.myapplication.ui.product.screens
import com.example.myapplication.data.Entities.Product
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.product.component.ProductsComponent

import androidx.compose.ui.unit.dp
@Composable
fun ProductsByCategoryScreen(
    category: String,
    products: List<Product>,
    onNavigateToDetails: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = category.uppercase(),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        ProductsComponent(
            products = products.filter { it.category == category },
            onNavigateToDetails = onNavigateToDetails,
            modifier = Modifier.fillMaxSize()
        )
    }
}