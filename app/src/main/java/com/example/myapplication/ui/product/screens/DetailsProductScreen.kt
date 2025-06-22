package com.example.myapplication.ui.product.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.Entities.Product
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.myapplication.ui.cart.CartIntent
import com.example.myapplication.ui.cart.CartViewModel

@Composable
fun DetailsProductScreen(
    product: Product,
    onBack: () -> Unit = {},
    cartViewModel: CartViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔙 Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
            }
            IconButton(onClick = { /* TODO panier */ }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Panier")
            }
        }

        // 🖼️ Carousel d’images produit
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(product.images) { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier
                        .width(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF2F2F2))
                )
            }
        }

        // 🏷️ Détails du produit
        Text(product.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text("Prix : ${product.price} DH", color = Color(0xFFDD0055), fontSize = 18.sp)

        Spacer(modifier = Modifier.height(10.dp))
        Text("Catégorie : ${product.category}", fontSize = 14.sp, color = Color.Gray)

        product.color?.let {
            Text("Couleur : $it", fontSize = 14.sp)
        }

        product.origin?.let {
            Text("Origine : $it", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 📐 Tailles disponibles
        if (product.sizes.isNotEmpty()) {
            Text("Tailles disponibles :", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(product.sizes) { size ->
                    Box(
                        modifier = Modifier
                            .border(
                                1.dp,
                                if (size.quantity > 0) Color.Black else Color.LightGray,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("${size.size} (${size.quantity})")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📝 Description
        Text("Description", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(product.description, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(24.dp))

        // 🛒 Ajouter au panier
        Button(
            onClick = {
                cartViewModel.onIntent(CartIntent.AddToCart(product))
                Toast.makeText(context, "Ajouté au panier", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Ajouter au panier", color = Color.White)
        }
    }
}
