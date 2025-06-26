package com.example.myapplication.ui.product.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.nav.Routes
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.screens.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CheckoutComponent(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    var currentStep by remember { mutableStateOf(1) } // 1: Livraison, 2: Paiement, 3: Confirmation

    AnimatedContent(
        targetState = currentStep,
        transitionSpec = {
            fadeIn() with fadeOut()
        },
        label = "CheckoutStepAnimation"
    ) { step ->
        when (step) {
            1 -> ShippingScreen(
                onContinue = { currentStep = 2 },
                onBack = { navController.popBackStack() }
            )
            2 -> PaymentScreen(
                onConfirm = { currentStep = 3 },
                onBack = { currentStep = 1 }
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
