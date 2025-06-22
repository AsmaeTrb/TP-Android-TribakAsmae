package com.example.myapplication.nav
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.ui.cart.CartScreen
import com.example.myapplication.ui.product.ProductViewModel
import com.example.myapplication.ui.product.ProductIntent
import com.example.myapplication.ui.product.screens.HomeScreen
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.screens.DetailsProductScreen

object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
    const val Cart = "cart"
}
@Composable
fun AppNavigation(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val navController = rememberNavController()

    val productState by productViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.handleIntent(ProductIntent.LoadProducts)
    }

    NavHost(navController = navController, startDestination = Routes.Home) {
        composable(Routes.Home) {
            HomeScreen(
                viewModel = productViewModel,
                onNavigateToDetails = { productId ->
                    navController.navigate("${Routes.ProductDetails}/$productId")
                },
                onCartClick = {
                    navController.navigate(Routes.Cart)
                }
            )
        }

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

        composable(Routes.Cart) {
            CartScreen(cartViewModel = cartViewModel)
        }
    }
}

