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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.nav.Routes
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.screens.*

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckoutComponent(
    navController: NavController,
    cartViewModel: CartViewModel,
    onBack: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("CHECKOUT") },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (currentStep == 1) {
                                navController.popBackStack()
                            } else {
                                currentStep--
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
                CheckoutProgressBar(currentStep = currentStep)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = { fadeIn() with fadeOut() },
                label = "CheckoutStepAnimation"
            ) { step ->
                when (step) {
                    1 -> ShippingScreen(
                        cartViewModel = cartViewModel,
                        onContinue = { currentStep = 2 },
                        onBack = { navController.popBackStack() }
                    )
                    2 -> PaymentScreen(
                        onConfirm = { currentStep = 3 },
                        onBack = { currentStep = 1 },
                        cartViewModel = cartViewModel,
                        )
                    3 -> ConfirmationScreen(
                        onFinish = {
                            navController.navigate(Routes.Home) {
                                popUpTo(0)
                            }
                        }
                    )
                }
            }
        }
    }
}
