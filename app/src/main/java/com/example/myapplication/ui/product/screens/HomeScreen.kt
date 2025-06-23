package com.example.myapplication.ui.product.screens

import ProductsComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.product.ProductIntent
import com.example.myapplication.ui.product.ProductViewModel

@Composable
fun HomeScreen(
    onNavigateToDetails: (String) -> Unit,
    onCartClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ProductIntent.LoadProducts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2E6D8))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCartClick) {
                Text("Panier")
            }
            Button(onClick = onLoginClick) {
                Text("Connexion")
            }
            Button(onClick = onRegisterClick) {
                Text("Créer un compte")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Liste des produits", fontSize = 20.sp)

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            state.error != null -> {
                Text(
                    text = "Erreur : ${state.error}",
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 16.sp
                )
            }

            else -> {
                ProductsComponent(
                    products = state.products,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }
    }
}
