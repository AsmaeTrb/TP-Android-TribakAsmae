package com.example.myapplication.nav

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myapplication.model.Product
object Routes {
    const val Home = "home"
    const val ProductDetails = "productDetails"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {

        composable(Routes.Home) {
            HomeScreen(onNavigateToDetails = { productId ->
                navController.navigate("${Routes.ProductDetails}/$productId")
            })
        }

        composable(
            "${Routes.ProductDetails}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            DetailsScreen(productId = productId)
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (String) -> Unit) {
    val products = listOf(
        Product(
            id = "0",
            nameProduct = "Effaclar DUO+M",
            brand = "La Roche-Posay",
            price = 129.99,
            description = "Soin complet contre les imperfections sévères : réduit boutons, points noirs et marques persistantes.",
            image = "./assets/image1.jpg",
            ingredients = listOf("Niacinamide", "Procerad", "LHA", "Acide salicylique"),
            howToUse = "Appliquer matin et soir sur le visage nettoyé, en évitant le contour des yeux.",
            quantity = 2
        ),
        Product(
            id = "1",
            nameProduct = "Hyalu B5 Sérum à l'Acide Hyaluronique",
            brand = "La Roche-Posay",
            price = 169.99,
            description = "Sérum réparateur anti-rides repulpant avec de l’acide hyaluronique pur et de la vitamine B5.",
            image = "./assets/image2.jpg",
            ingredients = listOf("Acide Hyaluronique", "Vitamine B5", "Madecassoside", "Eau thermale La Roche-Posay"),
            howToUse = "Appliquer matin et/ou soir sur le visage et le cou.",
            quantity = 10
        ),
        Product(
            id = "2",
            nameProduct = "Hydreane Légère Crème Hydratante",
            brand = "La Roche-Posay",
            price = 89.99,
            description = "Crème hydratante pour peaux normales à mixtes, enrichie en Eau Thermale de La Roche-Posay.",
            image = "./assets/image3.jpg",
            ingredients = listOf("Eau Thermale La Roche-Posay", "Squalane", "Glycérine", "Shea Butter"),
            howToUse = "Appliquer matin et/ou soir sur une peau propre, visage et cou.",
            quantity = 15
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Liste des produits")

        products.forEach { product ->
            Button(onClick = { onNavigateToDetails(product.id) }) {
                Text(text = "${product.nameProduct} - ${product.price} MAD")
            }
        }
    }
}

@Composable
fun DetailsScreen(productId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenue sur DetailsScreen")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Produit ID: $productId")
    }
}
