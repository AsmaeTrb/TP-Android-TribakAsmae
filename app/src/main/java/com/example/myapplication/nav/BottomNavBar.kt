package com.example.myapplication.nav

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.AuthViewModel

@Composable
fun BottomNavigationBar(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentUser by authViewModel.currentUser.collectAsState()
    val cartState by cartViewModel.state.collectAsState()

    NavigationBar {
        BottomNavItem.items.forEach { item ->
            val targetRoute = if (item == BottomNavItem.Profile && currentUser == null) {
                BottomNavItem.AuthWelcome.route
            } else {
                item.route
            }

            NavigationBarItem(
                icon = {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.label,
                            modifier = Modifier.size(30.dp)
                        )

                        if (item.route == "cart" && cartState.items.isNotEmpty()) {
                            Text(
                                text = cartState.items.sumOf { it.quantity }.toString(),
                                color = Color.Black,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Normal
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route ||
                        (item == BottomNavItem.Profile && currentRoute == BottomNavItem.AuthWelcome.route),
                onClick = {
                    navController.navigate(targetRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
