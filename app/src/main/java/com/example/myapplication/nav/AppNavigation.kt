package com.example.myapplication.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.myapplication.ui.cart.CartScreen
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.AuthViewModel
import com.example.myapplication.ui.product.ProductIntent
import com.example.myapplication.ui.product.ProductViewModel
import com.example.myapplication.ui.product.screens.*
import com.example.myapplication.ui.generateCategoryList.generateCategoryList

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
    const val Login = "login"
    const val Register = "register"
    const val Catalogue = "catalogue"
    const val ProductsByCategory = "products"
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

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
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
                    onLoginClick = { navController.navigate(Routes.Login) },
                    onRegisterClick = { navController.navigate(Routes.Register) }
                )
            }

            composable(
                "${Routes.ProductDetails}/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
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

            composable(Routes.Cart) {
                CartScreen(cartViewModel = cartViewModel)
            }

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

            composable(Routes.Catalogue) {
                CatalogueScreen(
                    products = productState.products,
                    onCategoryClick = { category ->
                        navController.navigate("${Routes.ProductsByCategory}/$category")
                    }
                )
            }

            composable("categories") {
                val categories = generateCategoryList(productState.products)
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "PRODUCTS",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(categories) { (category, imageUrl) ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clickable {
                                        navController.navigate("${Routes.ProductsByCategory}/$category")
                                    }
                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = category,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.3f))
                                )
                                Text(
                                    text = category.replaceFirstChar { it.uppercase() },
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(16.dp),
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
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
