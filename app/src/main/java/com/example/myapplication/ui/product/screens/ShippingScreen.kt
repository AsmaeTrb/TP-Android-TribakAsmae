package com.example.myapplication.ui.product.screens
import android.R.attr.padding
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.component.CheckoutProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingScreen(
    cartViewModel: CartViewModel ,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("USA") }
    var address by remember { mutableStateOf("") }
    var address2 by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var shippingMethod by remember { mutableStateOf("Home") }

    val cartState by cartViewModel.state.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "YOUR SHOPPING BAG (${cartState.items.size})",
                    style = MaterialTheme.typography.titleMedium
                )
                Text("View", color = Color.Blue)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Total US$ ${"%.2f".format(cartState.items.sumOf { it.product.price * it.quantity })}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("PROMOTIONAL CODE OR GIFT CARD")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Marago likes you")
            Text(
                "You need a minimum of 10:00 Likes to enjoy discounts with the Club.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("SHIPPING METHOD", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Column {
                ShippingOption(
                    selected = shippingMethod == "Home",
                    onSelect = { shippingMethod = "Home" },
                    title = "Home FREE",
                    delivery1 = "Friday, Jun 27 - Thursday, Jul 5",
                    delivery2 = "Thursday, Jul 5 - Monday, Jul 14"
                )

                Spacer(modifier = Modifier.height(8.dp))

                ShippingOption(
                    selected = shippingMethod == "Store",
                    onSelect = { shippingMethod = "Store" },
                    title = "Store Pickup",
                    delivery1 = "Pick up in nearest store",
                    delivery2 = "Available immediately"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("SHIPPING DETAILS", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = country,
                onValueChange = { country = it },
                label = { Text("Country") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = address2,
                onValueChange = { address2 = it },
                label = { Text("Address (line 2)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = zipCode,
                onValueChange = { zipCode = it },
                label = { Text("Zip Code") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state,
                onValueChange = { state = it },
                label = { Text("State") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Continue to payment")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "By continuing you confirm you have read the Privacy Policy",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

@Composable
fun ShippingOption(
    selected: Boolean,
    onSelect: () -> Unit,
    title: String,
    delivery1: String,
    delivery2: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onSelect() }
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(delivery1, style = MaterialTheme.typography.bodySmall)
            Text(delivery2, style = MaterialTheme.typography.bodySmall)
        }
    }
}
