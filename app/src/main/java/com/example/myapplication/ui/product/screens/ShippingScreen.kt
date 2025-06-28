package com.example.myapplication.ui.product.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                .padding(24.dp)
        ) {
            // Cart Summary Card avec bouton "View"
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Your Shopping Bag (${cartState.items.sumOf { it.quantity }})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            "View Details",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { isSheetOpen = true }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Mini product gallery
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.height(80.dp)
                    ) {
                        items(cartState.items.take(4)) { item ->
                            AsyncImage(
                                model = item.product.images.firstOrNull(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(MaterialTheme.shapes.small)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            "US$ ${"%.2f".format(cartState.items.sumOf { it.product.price * it.quantity })}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }

            // Le reste de votre code élégant (Shipping Method et Shipping Details Form)...
            // ... [Conservez tout le reste de votre implémentation existante] ...

            Spacer(modifier = Modifier.height(24.dp))

            // Shipping Method
            Text(
                "Shipping Method",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            "Standard Delivery",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            "FREE - Delivery between Jul 1 and Jul 5",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            // Shipping Details Form
            Text(
                "Shipping Details",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(12.dp))

            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                leadingIcon = { Icon(Icons.Default.Person, null, modifier = Modifier.size(20.dp)) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError != null,
                supportingText = { emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = textFieldColors,
                leadingIcon = { Icon(Icons.Default.Email, null, modifier = Modifier.size(20.dp)) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = textFieldColors,
                leadingIcon = { Icon(Icons.Default.Phone, null, modifier = Modifier.size(20.dp)) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                leadingIcon = { Icon(Icons.Default.Home, null, modifier = Modifier.size(20.dp)) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = zipCode,
                    onValueChange = { zipCode = it },
                    label = { Text("Zip Code") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors
                )

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(2f),
                    colors = textFieldColors
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val shippingAddress = Address(
                        fullName = name,
                        address = address,
                        postalCode = zipCode,
                        phone = phone,
                        city = city,
                        country = "FR",
                        email = email
                    )
                    onContinue(shippingAddress)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (allFieldsValid) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (allFieldsValid) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                enabled = allFieldsValid,
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    "Continue to Payment",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
