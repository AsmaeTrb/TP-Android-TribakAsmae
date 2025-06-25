package com.example.myapplication.ui.product.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.myapplication.data.Entities.ProductSize

@Composable
fun AddToCartSection(
    name: String,
    price: Double,
    sizes: List<ProductSize>,
    onAddToCart: (String) -> Unit,
    wishlistCount: Int = 7
) {
    var showSizeDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Ligne avec nom et icône
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name.uppercase(), fontSize = 13.sp, color = Color.Black)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "wishlist", tint = Color.Black)
                Text("+$wishlistCount", fontSize = 13.sp, modifier = Modifier.padding(start = 4.dp), color = Color.Black)
            }
        }

        // Prix
        Text(
            text = "${price} MAD",
            fontSize = 13.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Bouton Ajouter
        Button(
            onClick = { showSizeDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(0.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text("AJOUTER", fontWeight = FontWeight.Bold)
        }

        // Dialog de sélection de taille
        if (showSizeDialog) {
            AlertDialog(
                onDismissRequest = { showSizeDialog = false },
                confirmButton = {},
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        sizes.filter { it.quantity > 0 }.forEach { size ->
                            Text(
                                text = "${size.size} (${size.quantity} dispo)",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        showSizeDialog = false
                                        onAddToCart(size.size)
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("TROUVEZ VOTRE TAILLE", fontSize = 12.sp)
                    }
                }
            )
        }
    }
}
