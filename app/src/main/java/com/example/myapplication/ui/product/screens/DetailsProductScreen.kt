package com.example.myapplication.ui.product.screens

import android.widget.Toast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.ui.cart.CartIntent
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.component.AddToCartSection
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun DetailsProductScreen(
    product: Product,
    onBack: () -> Unit = {},
    cartViewModel: CartViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    var showSizeDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            AddToCartSection(
                name = product.name,
                price = product.price,
                sizes = product.sizes,
                onAddToCart = { selectedSize ->
                    showSizeDialog = false
                    cartViewModel.onIntent(CartIntent.AddToCart(product, selectedSize))
                    Toast.makeText(context, "Ajouté au panier", Toast.LENGTH_SHORT).show()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                }
                IconButton(onClick = {
                    navController.navigate("cart")
                }) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Panier")
                }
            }

            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .clip(RoundedCornerShape(0.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = product.description,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )

            product.color?.let {
                Text("Couleur : $it", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(start = 16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(product.images.drop(1)) { img ->
                    AsyncImage(
                        model = img,
                        contentDescription = null,
                        modifier = Modifier
                            .width(400.dp)
                            .height(500.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
