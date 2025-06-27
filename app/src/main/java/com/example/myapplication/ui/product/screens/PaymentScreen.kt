package com.example.myapplication.ui.product.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.product.component.PaymentCardOption
import com.example.myapplication.ui.product.component.SummaryRow

import com.example.myapplication.ui.cart.CartViewModel
import androidx.compose.runtime.getValue
import com.example.myapplication.data.Entities.CardDetails
import com.example.myapplication.data.Entities.PaymentMethod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    val cartState by cartViewModel.state.collectAsState()
    val subtotal = cartState.items.sumOf { it.product.price * it.quantity }
    val tax = subtotal * 0.05 // 5% tax
    val total = subtotal + tax

    var selectedMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var cardDetails by remember { mutableStateOf(CardDetails()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Text(
            "PAYMENT METHOD",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Carte bancaire option
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
                    onValueChange = {
                        if (it.length <= 16) cardDetails = cardDetails.copy(number = it)
                    },
                    label = { Text("Card number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = cardDetails.expiry,
                        onValueChange = {
                            if (it.length <= 4) cardDetails = cardDetails.copy(expiry = it)
                        },
                        label = { Text("MM/YY") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedTextField(
                        value = cardDetails.cvv,
                        onValueChange = {
                            if (it.length <= 3) cardDetails = cardDetails.copy(cvv = it)
                        },
                        label = { Text("CVV") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }

        // PayPal option
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

        // Order Summary
        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                "ORDER SUMMARY",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            SummaryRow("Subtotal", "US$ ${"%.2f".format(subtotal)}")
            SummaryRow("Delivery", "Free")
            SummaryRow("Estimated Tax", "US$ ${"%.2f".format(tax)}")

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            SummaryRow(
                "TOTAL",
                "US$ ${"%.2f".format(total)}",
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        // Pay Button
        Button(
            onClick = onConfirm,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = selectedMethod != null,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(
                when (selectedMethod) {
                    PaymentMethod.PAYPAL -> "Pay via PayPal"
                    else -> "Pay now"
                },
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        // Terms
        Text(
            "By completing the purchase, you confirm that you have read, understood and agree to the Terms and conditions.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
