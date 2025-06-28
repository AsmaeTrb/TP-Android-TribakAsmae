package com.example.myapplication.ui.product.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.ui.product.component.AddToCartSection

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import com.example.myapplication.data.Entities.Product
import com.example.myapplication.ui.cart.CartIntent
import com.example.myapplication.ui.cart.CartViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.data.Entities.User
import com.example.myapplication.data.Entities.WishlistItem
import com.example.myapplication.ui.product.WishlistViewModel


@Composable
fun DetailsProductScreen(
    product: Product,
    onBack: () -> Unit = {},
    cartViewModel: CartViewModel,
    navController: NavController,
    wishlistViewModel: WishlistViewModel,
    currentUser: User?
) {
    val context = LocalContext.current
    var showSizeDialog by remember { mutableStateOf(false) }

    val isFavorite by wishlistViewModel
        .isProductInWishlist(product.id)
        .collectAsState(initial = false)

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
            // 🖼️ Image principale avec icône cœur en overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
            ) {
                AsyncImage(
                    model = product.images.firstOrNull(),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(0.dp)),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour", tint = Color.Black)
                    }

                    Row {
                        IconButton(onClick = {
                            if (currentUser != null) {
                                if (isFavorite) {
                                    wishlistViewModel.removeFromWishlist(currentUser.id, product.id)
                                } else {
                                    val wishlistItem = WishlistItem(
                                        productId = product.id,
                                        name = product.name,
                                        price = product.price,
                                        image = product.images.firstOrNull() ?: ""
                                    )
                                    wishlistViewModel.addToWishlist(currentUser.id, wishlistItem)
                                }
                            } else {
                                Toast.makeText(context, "Veuillez vous connecter", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favori",
                                tint = if (isFavorite) Color.Red else Color.Black
                            )
                        }

                        IconButton(onClick = {
                            navController.navigate("cart")
                        }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Panier", tint = Color.Black)
                        }
                    }
                }
            }

            Text(
                text = product.description,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )

            product.color?.let {
                Text(
                    "Couleur : $it",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
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
