package com.example.myapplication.nav
import androidx.compose.runtime.getValue
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.ui.product.AuthViewModel

@Composable
fun BottomNavigationBar(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentUser by authViewModel.currentUser.collectAsState()

    NavigationBar {
        BottomNavItem.items.forEach { item ->
            val targetRoute = if (item == BottomNavItem.Profile && currentUser == null) {
                BottomNavItem.AuthWelcome.route
            } else {
                item.route
            }
            NavigationBarItem(
                icon = { Icon(painterResource(item.iconRes), contentDescription = item.label) },
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