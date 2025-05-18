// ui/product/screens/HomeScreen.kt
package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Product
import com.example.myapplication.ui.product.compoment.ProductItemComponent
@Composable
fun HomeScreen(onNavigateToDetails: (String) -> Unit) {
    val products = listOf(
        Product("0", "Effaclar DUO+M", "La Roche-Posay", 129.99, "...", "image1", listOf(), "...", 2),
        Product("1", "Hyalu B5", "La Roche-Posay", 169.99, "...", "image2", listOf(), "...", 10),
        Product("2", "Hydreane Légère", "La Roche-Posay", 89.99, "...", "image3", listOf(), "...", 15)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Liste des produits", fontSize = 20.sp)
        products.forEach { product ->
            ProductItemComponent(product = product, onClick = { onNavigateToDetails(product.id) })
        }
    }
}
