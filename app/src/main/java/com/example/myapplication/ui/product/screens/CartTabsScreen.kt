package com.example.myapplication.ui.product.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.myapplication.ui.cart.CartScreen
import com.example.myapplication.ui.cart.CartViewModel
import com.example.myapplication.ui.product.AuthViewModel
import androidx.compose.material3.Divider
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTabsScreen(
    cartViewModel: CartViewModel,
    authViewModel: AuthViewModel,
    onNavigateToAuth: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val cartState by cartViewModel.state.collectAsState()
    val isLoggedIn = authViewModel.currentUser.collectAsState().value != null

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = selectedTab,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color.Black,
                        height = 2.dp
                    )
                },
                divider = {
                    Divider(thickness = 1.dp, color = Color.LightGray)
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("PANIER")
                            if (cartState.items.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = cartState.items.size.toString(),
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                )

                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("FAVORIS") }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> CartScreen(cartViewModel) // ✅ tu réutilises directement CartScreen
                1 -> {
                    if (isLoggedIn) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Vos favoris s'afficheront ici")
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Connectez-vous pour accéder à vos favoris")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = onNavigateToAuth) {
                                Text("Se connecter")
                            }
                        }
                    }
                }
            }
        }
    }
}
