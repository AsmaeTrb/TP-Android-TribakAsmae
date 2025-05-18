package com.example.myapplication.ui.product.compoment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.model.Product

@Composable
fun ProductItemComponent(product: Product, onClick: () -> Unit) {
    val imageResId = when (product.image) {
        "image1" -> R.drawable.image1
        "image2" -> R.drawable.image2
        "image3" -> R.drawable.image3
        else -> null
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        imageResId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = product.nameProduct,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = product.nameProduct)
            Text(text = "${product.price} MAD")
        }

        Button(onClick = onClick) {
            Text("Détails")
        }
    }
}
