package com.example.myapplication.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.cart.CartScreen
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.*
import com.example.myapplication.ui.product.screens.*

object Routes {
    const val Home = "home"
    const val Profile = "profile"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
    const val Login = "login"
    const val Register = "register"
    const val Catalogue = "catalogue"
    const val ProductsByCategory = "products"
    const val AuthWelcome = "authWelcome"
}

@Composable
fun AppNavigation(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    sessionViewModel: SessionViewModel = viewModel()
) {
    val navController = rememberNavController()
    val productState by productViewModel.state.collectAsState()
    val loginState by authViewModel.loginState.collectAsState()
    val isLoggedIn by sessionViewModel.isLoggedIn.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.handleIntent(ProductIntent.LoadProducts)
    }

    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            sessionViewModel.login()
            navController.navigate(Routes.Profile) {
                popUpTo(Routes.Login) { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, authViewModel) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home) {
                HomeScreen(
                    viewModel = productViewModel,
                    onNavigateToDetails = { productId ->
                        navController.navigate("${Routes.ProductDetails}/$productId")
                    },
                    onCartClick = { navController.navigate(Routes.Cart) },
                    onLoginClick = { navController.navigate(Routes.Profile) },
                    onRegisterClick = { navController.navigate(Routes.Register) }
                )
            }

            composable(
                route = "${Routes.ProductDetails}/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                val product = productState.products.find { it.id == productId }

                if (product != null) {
                    DetailsProductScreen(
                        product = product,
                        cartViewModel = cartViewModel,
                        navController = navController, // ✅ ajout ici
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    Text("Produit non trouvé")
                }
            }

            composable(Routes.Cart) {
                CartScreen(cartViewModel = cartViewModel)
            }

            composable(Routes.Profile) {
                if (isLoggedIn && currentUser != null) {
                    ProfileScreen(
                        user = currentUser!!,
                        onLogout = {
                            sessionViewModel.logout()
                            navController.navigate(Routes.Home) {
                                popUpTo(Routes.Profile) { inclusive = true }
                            }
                        }
                    )
                } else {
                    AuthWelcomeScreen(
                        onNavigateToLogin = { navController.navigate(Routes.Login) },
                        onNavigateToRegister = { navController.navigate(Routes.Register) }
                    )
                }
            }

            composable(Routes.AuthWelcome) {
                AuthWelcomeScreen(
                    onNavigateToLogin = { navController.navigate(Routes.Login) },
                    onNavigateToRegister = { navController.navigate(Routes.Register) }
                )
            }

            composable(Routes.Register) {
                RegisterScreen(
                    authViewModel = authViewModel,
                    onBack = { navController.popBackStack() },
                    onRegisterSuccess = {
                        sessionViewModel.login()
                        navController.navigate(Routes.Profile) {
                            popUpTo(Routes.Register) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.Login) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        sessionViewModel.login()
                        navController.navigate(Routes.Profile) {
                            popUpTo(Routes.Login) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Routes.Register)
                    }
                )
            }

            composable(Routes.Catalogue) {
                CatalogueScreen(
                    products = productState.products,
                    onCategoryClick = { category ->
                        navController.navigate("${Routes.ProductsByCategory}/$category")
                    }
                )
            }

            composable(
                route = "${Routes.ProductsByCategory}/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: return@composable
                ProductsByCategoryScreen(
                    category = category,
                    products = productState.products,
                    onNavigateToDetails = { productId ->
                        navController.navigate("${Routes.ProductDetails}/$productId")
                    }
                )
            }
        }
    }
}
