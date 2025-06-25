package com.example.myapplication.ui.product.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.myapplication.data.Entities.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItemComponent(
    cartItem: CartItem,
    availableQuantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image du produit (avec fallback si vide)
            val imageUrl = cartItem.product.images.firstOrNull()

            if (!imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("—", color = Color.DarkGray)
                }
            }

            // Détails du produit
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = cartItem.product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Taille: ${cartItem.selectedSize}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${cartItem.product.price} DH",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Stock: $availableQuantity",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Contrôles de quantité
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = onAdd,
                    modifier = Modifier.size(36.dp),
                    enabled = cartItem.quantity < availableQuantity
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Augmenter",
                        tint = if (cartItem.quantity < availableQuantity) Color.Black else Color.LightGray
                    )
                }

                Text(
                    text = cartItem.quantity.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(36.dp),
                    enabled = cartItem.quantity > 1
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Diminuer",
                        tint = if (cartItem.quantity > 1) Color.Black else Color.LightGray
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Supprimer",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
