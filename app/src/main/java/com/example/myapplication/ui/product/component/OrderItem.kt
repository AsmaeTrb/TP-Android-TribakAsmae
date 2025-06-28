package com.example.myapplication.ui.product.component
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Entities.Order
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderItem(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Commande n° ${order.userId}", fontWeight = FontWeight.Bold)
            Text("Montant : %.2f €".format(order.total))
            Text("Statut : ${order.status ?: "Inconnu"}")
            Text("Client : ${order.email}")
        }
    }
}

private fun formatDate(dateStr: String?): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = parser.parse(dateStr ?: "")
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        formatter.format(date ?: Date())
    } catch (e: Exception) {
        "Date inconnue"
    }
}
