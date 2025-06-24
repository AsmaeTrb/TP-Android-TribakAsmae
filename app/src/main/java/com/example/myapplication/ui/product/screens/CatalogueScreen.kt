package com.example.myapplication.ui.product.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.Entities.Product
import com.example.myapplication.ui.generateCategoryList.generateCategoryList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.math.abs
@Composable
fun CatalogueScreen(
    products: List<Product>,
    onCategoryClick: (String) -> Unit
) {
    val categories = remember(products) { generateCategoryList(products) }
    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableIntStateOf(0) }

    // 📍 Détecter l’élément centré
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .distinctUntilChanged()
            .collect { visibleItems ->
                val center = listState.layoutInfo.viewportStartOffset +
                        (listState.layoutInfo.viewportEndOffset - listState.layoutInfo.viewportStartOffset) / 2

                visibleItems.minByOrNull {
                    abs((it.offset + it.size / 2) - center)
                }?.index?.let {
                    selectedIndex = it
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 🌄 Image dynamique
        AsyncImage(
            model = categories.getOrNull(selectedIndex)?.second ?: "",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 🌓 Overlay noir
        Box(
            Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "PRODUCTS",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ✅ Liste centrée
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    contentPadding = PaddingValues(vertical = 300.dp) // 👈 Ajoute du vide haut/bas
                ) {
                    itemsIndexed(categories) { index, (category, _) ->
                        val isSelected = index == selectedIndex
                        val fontSize by animateDpAsState(
                            targetValue = if (isSelected) 24.dp else 18.dp,
                            animationSpec = tween(200)
                        )
                        val color by animateColorAsState(
                            targetValue = if (isSelected) Color.White else Color.LightGray,
                            animationSpec = tween(200)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable(enabled = isSelected) {
                                    onCategoryClick(category.lowercase())
                                }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = category.uppercase(),
                                fontSize = fontSize.value.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = color
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
