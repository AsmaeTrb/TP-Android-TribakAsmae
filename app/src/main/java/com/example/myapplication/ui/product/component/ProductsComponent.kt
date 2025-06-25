package com.example.myapplication.ui.product.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Entities.Product

@Composable
fun ProductsComponent(
    products: List<Product>,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp), // ✅ pas de marge extérieure
        verticalArrangement = Arrangement.spacedBy(0.dp), // ✅ pas d’espace vertical
        horizontalArrangement = Arrangement.spacedBy(0.dp) // ✅ pas d’espace horizontal
    ) {
        items(products) { product ->
            ProductItemComponent(
                product = product,
                onClick = { onNavigateToDetails(product.id) }
            )
        }
    }
}
