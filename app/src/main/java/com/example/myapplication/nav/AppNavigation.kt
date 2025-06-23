package com.example.myapplication.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.cart.CartScreen
import com.example.myapplication.ui.product.ProductViewModel
import com.example.myapplication.ui.product.ProductIntent
import com.example.myapplication.ui.product.AuthViewModel
import com.example.myapplication.ui.product.screens.*
import com.example.myapplication.ui.cart.CartViewModel

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
    const val Login = "login"
    const val Register = "register"
}

@Composable
fun AppNavigation(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val navController = rememberNavController()
    val productState by productViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.handleIntent(ProductIntent.LoadProducts)
    }

    NavHost(navController = navController, startDestination = Routes.Home) {

        // 🏠 Home
        composable(Routes.Home) {
            HomeScreen(
                viewModel = productViewModel,
                onNavigateToDetails = { productId ->
                    navController.navigate("${Routes.ProductDetails}/$productId")
                },
                onCartClick = { navController.navigate(Routes.Cart) },
                onLoginClick = { navController.navigate(Routes.Login) },
                onRegisterClick = { navController.navigate(Routes.Register) }
            )
        }

        // 🛍️ Product Details
        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = productState.products.find { it.id == productId }

            if (product != null) {
                DetailsProductScreen(
                    product = product,
                    cartViewModel = cartViewModel,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Text("Produit non trouvé")
            }
        }

        // 🛒 Cart
        composable(Routes.Cart) {
            CartScreen(cartViewModel = cartViewModel)
        }

        // 🔐 Login
        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.Register)
                },
                authViewModel = authViewModel
            )
        }

        // 📝 Register
        composable(Routes.Register) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Register) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Routes.Login) {
                        popUpTo(Routes.Register) { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }
    }
}
