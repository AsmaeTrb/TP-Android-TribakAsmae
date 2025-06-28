package com.example.myapplication.nav

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.cart.CartScreen
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.order.OrderViewModel
import com.example.myapplication.ui.product.*
import com.example.myapplication.ui.product.component.CheckoutComponent
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
    const val Splash = "splash"
    const val Checkout = "checkout"
    const val Shipping = "shipping"
    const val Payment = "payment"
    const val OrderConfirmation = "confirmation"
}

@Composable
fun AppNavigation(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    orderViewModel: OrderViewModel = viewModel()
) {
    val navController = rememberNavController()
    val productState by productViewModel.state.collectAsState()
    val loginState by authViewModel.loginState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            val currentRoute = navController.currentDestination?.route
            val isAuthRoute = currentRoute in listOf(
                Routes.Login,
                Routes.Register,
                Routes.AuthWelcome,
                Routes.Splash
            )

            if (!isAuthRoute) {
                navController.navigate(Routes.AuthWelcome) {
                    popUpTo(Routes.Home) { inclusive = false }
                    launchSingleTop = true
                }
            }
        }
    }

    val isLoggedIn = currentUser != null
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        productViewModel.handleIntent(ProductIntent.LoadProducts)
    }

    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            currentUser?.let { cartViewModel.loadCartForUser(it.id, productState.products) }

            val currentEntry = navController.currentBackStackEntry
            val shouldRedirect = currentEntry
                ?.savedStateHandle
                ?.get<Boolean>("redirect_to_Checkout") ?: false

            // Supprimer le flag après lecture pour éviter boucle infinie
            currentEntry?.savedStateHandle?.remove<Boolean>("redirect_to_Checkout")

            if (shouldRedirect) {
                // Supprimer Cart de la stack sinon on revient dessus
                navController.popBackStack(Routes.Cart, inclusive = true)

                // Aller vers Checkout
                navController.navigate(Routes.Checkout)
            } else {
                navController.navigate(Routes.Profile) {
                    popUpTo(Routes.Login) { inclusive = true }
                }
            }

            authViewModel.handleIntent(AuthIntent.ResetState)
        }
    }


    Scaffold(
        bottomBar = { BottomNavigationBar(navController, authViewModel) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Splash,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Splash) {
                SplashScreen(navController)
            }

            composable(Routes.Home) {
                HomeScreen(
                    viewModel = productViewModel,
                    onNavigateToDetails = { productId ->
                        navController.navigate("${Routes.ProductDetails}/$productId") {
                            launchSingleTop = true
                        }
                    }
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
                        navController = navController,
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    Text("Produit non trouvé")
                }
            }

            composable(Routes.Cart) {
                CartTabsScreen(
                    cartViewModel = cartViewModel,
                    authViewModel = authViewModel,
                    onNavigateToAuth = {
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("redirect_to_Checkout", true)

                        navController.navigate(Routes.Login) {
                            popUpTo(Routes.Cart) { inclusive = true } // Retire l'écran panier
                        }
                    },
                    onNavigateToCheckout = {
                        navController.navigate(Routes.Checkout )
                    }
                )
            }

            composable(Routes.Profile) {
                if (isLoggedIn) {
                    ProfileScreen(
                        user = currentUser!!,
                        onLogout = {
                            authViewModel.handleIntent(AuthIntent.Logout)
                            cartViewModel.clearCart()
                            Toast.makeText(context, "Déconnexion réussie", Toast.LENGTH_SHORT).show()

                            navController.navigate(Routes.Login) {
                                popUpTo(Routes.Home) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        orderViewModel = orderViewModel,
                        navController = navController
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
                        navController.navigate(Routes.Profile) {
                            popUpTo(Routes.Register) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.Login) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = { /* Géré via LaunchedEffect */ },
                    onNavigateToRegister = { navController.navigate(Routes.Register) }
                )
            }
            composable(
                route = "orderDetail/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")
                // Affiche les détails de la commande ici
                Text("Détails de la commande de l’utilisateur $userId") // à remplacer plus tard
            }

            composable(Routes.Checkout) {
                if (currentUser != null) {
                    CheckoutComponent(
                        navController = navController,
                        cartViewModel = cartViewModel ,
                        orderViewModel = orderViewModel,
                        onBack = { navController.popBackStack() },
                        currentUser = currentUser // ✅ Ajout de ce paramètre obligatoire
// ✅ très important
                    )
                } else {
                    LaunchedEffect(Unit) {
                        Toast
                            .makeText(context, "Veuillez vous connecter", Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate(Routes.Login) {
                            popUpTo(Routes.Checkout) { inclusive = true }
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
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
