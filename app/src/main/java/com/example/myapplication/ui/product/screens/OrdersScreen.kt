package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.order.OrderViewModel
import com.example.myapplication.ui.product.component.OrderItem
import com.example.myapplication.ui.product.component.EmptyOrdersPlaceholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavController,
    onLogout: () -> Unit,
    orderViewModel: OrderViewModel
) {
    val orders by orderViewModel.userOrders.collectAsState()
    val isLoading by orderViewModel.isLoading.collectAsState()
    val sessionUser = remember { orderViewModel.currentUser }

    LaunchedEffect(Unit) {
        if (sessionUser?.role?.name == "ADMIN") {
            orderViewModel.loadOrders(null)
        } else {
            orderViewModel.loadOrders(sessionUser?.id)
        }
    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (sessionUser?.role?.name == "ADMIN") {
                Text(
                    text = "Vue administrateur",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            strokeWidth = 3.dp
                        )
                    }
                }
                orders.isEmpty() -> {
                    EmptyOrdersPlaceholder(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 32.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(orders) { order ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                OrderItem(
                                    order = order,
                                    onClick = {
                                        navController.navigate("orderDetail/${order.userId}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
