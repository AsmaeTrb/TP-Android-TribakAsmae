package com.example.myapplication.ui.product.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Entities.*
import com.example.myapplication.data.Session.SessionManager
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.component.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel,
    shippingAddress: Address,
    onConfirm: (Order) -> Unit,
    onBack: () -> Unit
) {
    val cartState by cartViewModel.state.collectAsState()
    val subtotal = cartState.items.sumOf { it.product.price * it.quantity }
    val tax = subtotal * 0.05
    val total = subtotal + tax
    val context = LocalContext.current

    val session = remember { SessionManager(context) }
    val user = session.getUser()

    var selectedMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var cardDetails by remember { mutableStateOf(CardDetails()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("PAYMENT METHOD", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp))

        PaymentCardOption(
            title = "CREDIT OR DEBIT CARD",
            isSelected = selectedMethod == PaymentMethod.CARD,
            onClick = { selectedMethod = PaymentMethod.CARD }
        )

        if (selectedMethod == PaymentMethod.CARD) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                OutlinedTextField(
                    value = cardDetails.holder,
                    onValueChange = { cardDetails = cardDetails.copy(holder = it) },
                    label = { Text("Card holder") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = cardDetails.number,
                    onValueChange = { if (it.length <= 16) cardDetails = cardDetails.copy(number = it) },
                    label = { Text("Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = cardDetails.expiry,
                        onValueChange = { if (it.length <= 4) cardDetails = cardDetails.copy(expiry = it) },
                        label = { Text("MM/YY") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = CreditCardExpiryVisualTransformation()
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedTextField(
                        value = cardDetails.cvv,
                        onValueChange = { if (it.length <= 3) cardDetails = cardDetails.copy(cvv = it) },
                        label = { Text("CVV") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("WE ACCEPT", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(vertical = 8.dp)) {
                    Image(painter = painterResource(R.drawable.ic_visa), contentDescription = "Visa", modifier = Modifier.size(40.dp))
                    Image(painter = painterResource(R.drawable.ic_mastercard), contentDescription = "MasterCard", modifier = Modifier.size(40.dp))
                    Image(painter = painterResource(R.drawable.ic_diners), contentDescription = "Diners Club", modifier = Modifier.size(40.dp))
                }
            }
        }

        PaymentCardOption(
            title = "PAYPAL",
            isSelected = selectedMethod == PaymentMethod.PAYPAL,
            onClick = { selectedMethod = PaymentMethod.PAYPAL }
        )

        if (selectedMethod == PaymentMethod.PAYPAL) {
            Text(
                "You will be redirected to PayPal to complete your purchase",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Column {
            Text("ORDER SUMMARY", style = MaterialTheme.typography.titleMedium)
            SummaryRow("Subtotal", "US$ ${"%.2f".format(subtotal)}")
            SummaryRow("Delivery", "Free")
            SummaryRow("Estimated Tax", "US$ ${"%.2f".format(tax)}")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryRow("TOTAL", "US$ ${"%.2f".format(total)}", textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        }

        Button(
            onClick = {
                try {
                    if (selectedMethod == PaymentMethod.PAYPAL) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/signin"))
                        context.startActivity(intent)
                    } else {
                        if (user == null) {
                            println("❌ Utilisateur non trouvé")
                            return@Button
                        }

                        val order = Order(
                            userId = user.id,
                            items = cartState.items,
                            total = total,
                            email = shippingAddress.email.takeIf { it.isNotBlank() } ?: user.email ?: "email@inconnu.com",

                            shippingAddress = shippingAddress
                        )

                        println("✅ Commande prête : $order")

                        try {
                            onConfirm(order)
                        } catch (e: Exception) {
                            println("💥 ERREUR dans onConfirm: ${e.message}")
                        }
                    }
                } catch (e: Exception) {
                    println("❌ ERREUR générale : ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = selectedMethod != null,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
        ) {
            Text(
                when (selectedMethod) {
                    PaymentMethod.PAYPAL -> "Pay via PayPal"
                    else -> "Pay now"
                },
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Text(
            "By completing the purchase, you confirm that you have read, understood and agree to the Terms and conditions.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
