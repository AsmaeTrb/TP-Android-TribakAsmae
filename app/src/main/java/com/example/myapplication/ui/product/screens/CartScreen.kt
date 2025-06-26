package com.example.myapplication.ui.cart

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.product.AuthViewModel
import com.example.myapplication.ui.product.component.CartItemComponent

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    authViewModel: AuthViewModel,
    onNavigateToAuth: () -> Unit,
    onNavigateToCheckout: () -> Unit
) {
    val state by cartViewModel.state.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🛒 En-tête
        Text(
            text = "Votre Panier",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (state.items.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Votre panier est vide", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            // 📦 Liste des articles
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.items) { cartItem ->
                    val availableQuantity = cartItem.product.sizes
                        .firstOrNull { it.size == cartItem.selectedSize }?.quantity ?: 0

                    CartItemComponent(
                        cartItem = cartItem,
                        availableQuantity = availableQuantity,
                        onAdd = {
                            if (cartItem.quantity < availableQuantity) {
                                cartViewModel.onIntent(
                                    CartIntent.ChangeQuantity(
                                        cartItem.product,
                                        cartItem.quantity + 1
                                    )
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Quantité maximale disponible atteinte",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onRemove = {
                            cartViewModel.onIntent(
                                CartIntent.ChangeQuantity(
                                    cartItem.product,
                                    cartItem.quantity - 1
                                )
                            )
                        },
                        onDelete = {
                            cartViewModel.onIntent(CartIntent.RemoveFromCart(cartItem.product))
                        }
                    )
                }
            }

            // ✅ Total + bouton commande
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total: ${state.items.sumOf { it.product.price * it.quantity }} DH",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (currentUser != null) {
                            onNavigateToCheckout()
                        } else {
                            onNavigateToAuth()
                            Toast.makeText(
                                context,
                                "Connectez-vous pour finaliser votre commande",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("PASSER LA COMMANDE", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
