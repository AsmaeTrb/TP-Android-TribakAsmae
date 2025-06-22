package com.example.myapplication.ui.cart
import com.example.myapplication.data.Entities.CartItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    val state by cartViewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Panier",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.items.isEmpty()) {
            Text("Votre panier est vide.", fontSize = 18.sp)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.items) { cartItem ->
                    CartItemComponent(
                        cartItem = cartItem,
                        onAdd = {
                            cartViewModel.onIntent(
                                CartIntent.ChangeQuantity(
                                    cartItem.product,
                                    cartItem.quantity + 1
                                )
                            )
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
        }
    }
}

@Composable
fun CartItemComponent(
    cartItem: CartItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = cartItem.product.images.firstOrNull(),
                contentDescription = cartItem.product.name,
                modifier = Modifier.size(72.dp).padding(end = 12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(cartItem.product.name, style = MaterialTheme.typography.titleMedium)
                Text("Prix unitaire: ${cartItem.product.price} DH", style = MaterialTheme.typography.bodyMedium)
                Text("Quantité: ${cartItem.quantity}", style = MaterialTheme.typography.bodyMedium)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = onAdd, modifier = Modifier.size(32.dp)) {
                    Text("+")
                }
                Button(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Text("-")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDelete, modifier = Modifier.size(32.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                    Text("X", color = MaterialTheme.colorScheme.onError)
                }
            }
        }
    }
}
