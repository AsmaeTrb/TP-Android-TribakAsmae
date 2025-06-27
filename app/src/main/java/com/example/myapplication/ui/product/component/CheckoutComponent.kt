package com.example.myapplication.ui.product.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.Session.SessionManager
import com.example.myapplication.data.Entities.User
import com.example.myapplication.nav.Routes
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.order.OrderIntent
import com.example.myapplication.ui.order.OrderState
import com.example.myapplication.ui.order.OrderViewModel
import com.example.myapplication.ui.product.screens.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CheckoutComponent(
    navController: NavController,
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel,  // Reçu en paramètre
    onBack: () -> Unit,
    currentUser: User? // ✅ ajouté pour passer l'utilisateur connecté
) {
    var currentStep by remember { mutableStateOf(1) }
    val shippingAddress by orderViewModel.shippingAddress.collectAsState()
    val orderState by orderViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("CHECKOUT") },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (currentStep == 1) onBack() else currentStep--
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
                CheckoutProgressBar(currentStep = currentStep)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (currentStep) {
                1 -> ShippingScreen(
                    cartViewModel = cartViewModel,
                    onContinue = { address ->
                        orderViewModel.setShippingAddress(address)
                        currentStep = 2
                    },
                    onBack = onBack
                )

                2 -> shippingAddress?.let { address ->
                    PaymentScreen(
                        cartViewModel = cartViewModel,
                        shippingAddress = address,
                        onConfirm = { order ->
                            orderViewModel.handleIntent(OrderIntent.PlaceOrder(order))
                            currentStep = 3
                        },
                        onBack = { currentStep = 1 }
                    )
                }

                3 -> {
                    when (orderState) {
                        is OrderState.Success -> {
                            LaunchedEffect(Unit) {
                                currentUser?.let {
                                    cartViewModel.clearCartForUser(it.id)  // ✅ Vider le panier côté backend et local
                                }
                            }
                            ConfirmationScreen(
                                order = (orderState as OrderState.Success).order,
                                orderViewModel = orderViewModel,  // On passe le même ViewModel
                                onFinish = {
                                    navController.navigate(Routes.Home) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        is OrderState.Error -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "❌ Erreur : ${(orderState as OrderState.Error).message}",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Button(
                                    onClick = { currentStep = 2 },
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text("Réessayer")
                                }
                            }
                        }
                        OrderState.Loading -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                                Text("Traitement de votre commande...")
                            }
                        }
                        OrderState.Idle -> {
                            // Cas initial, ne devrait pas arriver ici
                        }
                    }
                }
            }
        }
    }
}