package com.example.myapplication.ui.product.component
import androidx.compose.foundation.background
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
) {
    var showSizeDialog by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf(sizes.firstOrNull()?.size ?: "") }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Ligne avec nom et icône
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name.uppercase(),
                fontSize = 13.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            }
        }

        // Prix
        Text(
            text = "${price} MAD",
            fontSize = 13.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold
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
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Text("AJOUTER", fontWeight = FontWeight.Bold)
        }

        // Dialog de sélection de taille amélioré
        if (showSizeDialog) {
            AlertDialog(
                onDismissRequest = { showSizeDialog = false },
                shape = RoundedCornerShape(8.dp),
                title = {
                    Text(
                        text = "TROUVEZ VOTRE TAILLE",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Liste des tailles disponibles
                        sizes.filter { it.quantity > 0 }.forEach { size ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        selectedSize = size.size
                                    }
                                    .background(
                                        if (selectedSize == size.size) Color.LightGray else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = size.size,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${size.quantity} disponible(s)",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (selectedSize.isNotEmpty()) {
                                onAddToCart(selectedSize)
                                showSizeDialog = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(0.dp),
                        enabled = selectedSize.isNotEmpty()
                    ) {
                        Text("CONFIRMER", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    }
