package com.example.myapplication.ui.product.screens
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.cart.CartViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingSummaryModal(
    cartViewModel: CartViewModel,
    sheetState: SheetState,
    onClose: () -> Unit
) {
    val cartState by cartViewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            // Header with item count
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "YOUR SHOPPING BAG (${cartState.items.sumOf { it.quantity }})",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                IconButton(onClick = onClose) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Items list
            cartState.items.forEach { item ->
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Product image
                        AsyncImage(
                            model = item.product.images.firstOrNull(),
                            contentDescription = item.product.name,
                            modifier = Modifier
                                .size(200.dp)
                                .padding(end = 12.dp)
                        )

                        // Product details
                        Column(modifier = Modifier.weight(1f)) {
                            // Product name (truncated if too long)
                            Text(
                                text = item.product.name.uppercase(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Product details
                            Text(
                                "Quantity: ${item.quantity}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                "Size: ${item.selectedSize}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                item.product.color ?: "",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Remove button
                            Text(
                                "Remove 1 item",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .clickable {
                                        scope.launch {
                                            cartViewModel.removeOneItem(item)
                                        }
                                    }
                            )
                        }

                        // Price column
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "US$ ${"%.2f".format(item.product.price)}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                "US$ ${"%.2f".format(item.product.price * item.quantity)}",
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(top = 12.dp),
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
            }

            // Order summary
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal")
                    Text("US$ ${"%.2f".format(cartState.items.sumOf { it.product.price * it.quantity })}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Delivery")
                    Text("Free", color = Color.Green)
                }

            }

            // Total
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                thickness = 0.5.dp,
                color = Color.LightGray
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "TOTAL",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "US$ ${"%.2f".format(cartState.items.sumOf { it.product.price * it.quantity })}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp)) // Space for scrolling
        }
    }
}