package com.example.myapplication.ui.product.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.product.ProductIntent
import com.example.myapplication.ui.product.ProductViewModel
@Composable
fun DetailsProductScreen(
    productId: String,
    viewModel: ProductViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        if (state.products.isEmpty()) {
            viewModel.handleIntent(ProductIntent.LoadProducts)
        }
    }

    val product = state.products.find { it.id == productId }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)) // gris très clair
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        product?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val imageResId = when (it.image) {
                        "image1" -> R.drawable.image1
                        "image2" -> R.drawable.image2
                        "image3" -> R.drawable.image3
                        else -> null
                    }

                    imageResId?.let { img ->
                        Image(
                            painter = painterResource(id = img),
                            contentDescription = it.nameProduct,
                            modifier = Modifier
                                .size(160.dp)
                                .padding(bottom = 12.dp)
                        )
                    }

                    Text(
                        text = it.nameProduct,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "Marque : ${it.brand}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = "Prix : ${it.price} MAD",
                        fontSize = 16.sp,
                        color = Color(0xFFD81B60), // rose
                        fontWeight = FontWeight.SemiBold
                    )

                    Divider(color = Color.LightGray, thickness = 1.dp)

                    Column(horizontalAlignment = Alignment.Start) {
                        Text("📝 Description :", fontWeight = FontWeight.Bold)
                        Text(it.description)

                        Spacer(Modifier.height(8.dp))
                        Text("🧴 Utilisation :", fontWeight = FontWeight.Bold)
                        Text(it.howToUse)

                        Spacer(Modifier.height(8.dp))
                        Text("🧪 Ingrédients :", fontWeight = FontWeight.Bold)
                        Text(it.ingredients.joinToString(", "))

                        Spacer(Modifier.height(8.dp))
                        Text("📦 Quantité : ${it.quantity}")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.popBackStack() },
                        shape = RoundedCornerShape(40),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFFD81B60)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFD81B60))
                    ) {
                        Text("← Retour")
                    }
                }
            }
        } ?: Text("Produit introuvable", color = Color.Red)
    }
}

