package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.Image
import com.example.myapplication.ui.product.WishlistViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.Entities.WishlistItem

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel,
    onRemove: (WishlistItem) -> Unit
) {
    val wishlist by viewModel.wishlist.collectAsState()

    if (wishlist.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Votre liste de favoris est vide.")
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(wishlist) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(item.image),
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 12.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.name, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "${item.price} MAD", style = MaterialTheme.typography.bodyMedium)
                        }

                        Button(
                            onClick = { onRemove(item) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            )
                        ) {
                            Text("Supprimer")
                        }
                    }
                }
            }
        }
    }
}
