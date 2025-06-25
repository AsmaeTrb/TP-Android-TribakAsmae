package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Text
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.myapplication.nav.Routes

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(true) {
        delay(3000) // Affiche pendant 3 secondes
        navController.navigate(Routes.Home) {
            popUpTo(Routes.Splash) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TRISMAE",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif, // Change si tu veux une autre typo
            textAlign = TextAlign.Center
        )
    }
}
