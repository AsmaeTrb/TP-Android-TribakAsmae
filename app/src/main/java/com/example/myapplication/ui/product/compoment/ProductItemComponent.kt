package com.example.myapplication.ui.product.compoment
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Entities.Product
@Composable
fun ProductItemComponent(product: Product, onClick: () -> Unit) {
    val imageResId = when (product.image) {
        "image1" -> R.drawable.image1
        "image2" -> R.drawable.image2
        "image3" -> R.drawable.image3
        else -> null
    }

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F0)) // rose clair
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clickable { onClick() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imageResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = product.nameProduct,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.nameProduct.uppercase(),
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
        }
    }
}
