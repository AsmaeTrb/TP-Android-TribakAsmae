package com.example.myapplication.ui.product.screens
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Entities.Order
import com.example.myapplication.ui.order.OrderIntent
import com.example.myapplication.ui.order.OrderState
import com.example.myapplication.ui.order.OrderViewModel
@Composable
fun ConfirmationScreen(
    order: Order,
    orderViewModel: OrderViewModel,  // Plus de viewModel() par défaut
    onFinish: () -> Unit
) {
    val state by orderViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            is OrderState.Success -> {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Commande confirmée",
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    "Commande confirmée !",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Numéro: #${order.userId}")
                Text("Total: ${"%.2f".format(order.total)} €")
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Retour à l'accueil")
                }
            }
            OrderState.Loading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Validation en cours...")
            }
            is OrderState.Error -> {
                Text(
                    "Erreur : ${(state as OrderState.Error).message}",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onFinish() }) {
                    Text("Retour")
                }
            }
            OrderState.Idle -> {
                Text("Préparation de la confirmation...")
            }
        }
    }
}