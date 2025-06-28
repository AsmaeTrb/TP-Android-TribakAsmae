package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.Entities.Address
import com.example.myapplication.ui.cart.CartViewModel
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingScreen(
    cartViewModel: CartViewModel,
    onContinue: (Address) -> Unit,

    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val cartState by cartViewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by remember { mutableStateOf(false) }

    // États du formulaire
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    // Validation
    var isEmailValid by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    val requiredFields = listOf(name, email, phone, address, zipCode, city)
    val allFieldsValid = requiredFields.all { it.isNotBlank() } && isEmailValid

    LaunchedEffect(email) {
        isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        emailError = if (email.isNotBlank() && !isEmailValid) "Email invalide" else null
    }

    LaunchedEffect(isSheetOpen) {
        if (isSheetOpen) {
            scrollState.scrollTo(0)
            bottomSheetState.show()
        }
    }

    if (isSheetOpen) {
        ShippingSummaryModal(
            cartViewModel = cartViewModel,
            sheetState = bottomSheetState,
            onClose = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    isSheetOpen = false
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Header du panier
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "YOUR SHOPPING BAG (${cartState.items.sumOf { it.quantity }})",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "View",
                color = Color.Blue,
                modifier = Modifier.clickable { isSheetOpen = true }
            )
        }

        // Aperçu des produits
        Row {
            cartState.items.take(3).forEach { item ->
                AsyncImage(
                    model = item.product.images.firstOrNull(),
                    contentDescription = item.product.name,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 8.dp)
                )
            }
        }

        // Total
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                "Total: US$ ${"%.2f".format(cartState.items.sumOf { it.product.price * it.quantity })}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        // Section livraison
        Text("SHIPPING METHOD", style = MaterialTheme.typography.titleMedium)
        var shippingMethod by remember { mutableStateOf("Home") }
        ShippingOption(
            selected = shippingMethod == "Home",
            onSelect = { shippingMethod = "Home" },
            title = "Home FREE",
            delivery1 = "Friday, Jun 27 - Thursday, Jul 5",
            delivery2 = "Thursday, Jul 5 - Monday, Jul 14"
        )
        ShippingOption(
            selected = shippingMethod == "Store",
            onSelect = { shippingMethod = "Store" },
            title = "Store Pickup",
            delivery1 = "Pick up in nearest store",
            delivery2 = "Available immediately"
        )

        // Formulaire
        Text("SHIPPING DETAILS", style = MaterialTheme.typography.titleMedium)

        // Champs obligatoires
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name *") },
            modifier = Modifier.fillMaxWidth(),
            isError = name.isEmpty() && name.isNotBlank()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email *") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it, color = Color.Red) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone *") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address *") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = zipCode,
            onValueChange = { zipCode = it },
            label = { Text("Zip Code *") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City *") },
            modifier = Modifier.fillMaxWidth()
        )

        // Bouton de paiement
        Button(
            onClick = {
                val shippingAddress = Address(
                    fullName = name,
                    address = address,             // ✅ correspond à backend
                    postalCode = zipCode,          // ✅
                    phone = phone,                 // ✅
                    city = city,
                    country = "FR",
                    email = email   // ✅ ici tu mets le vrai email saisi

                )
                onContinue(shippingAddress)

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (allFieldsValid) Color.Black else Color.Gray,
                contentColor = Color.White
            ),
            enabled = allFieldsValid
        ) {
            Text("Continue to payment")
        }

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
        RadioButton(selected = selected, onClick = onSelect)
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(delivery1, style = MaterialTheme.typography.bodySmall)
            Text(delivery2, style = MaterialTheme.typography.bodySmall)
        }
    }
}
