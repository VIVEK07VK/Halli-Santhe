package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.components.*
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.CartViewModel

enum class PaymentMethod(val label: String, val icon: ImageVector) {
    UPI("UPI Payment", Icons.Default.QrCode),
    COD("Cash on Delivery", Icons.Default.Payments)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onProceedToPayment: () -> Unit
) {
    val cartSummary by cartViewModel.cartSummary.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Summary", fontWeight = FontWeight.Bold, color = PrimaryGreenDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryGreenDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Total", fontSize = 12.sp, color = TextSecondary)
                        Text("₹${String.format("%.2f", cartSummary.finalTotal)}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = PrimaryGreenDark)
                    }
                    Button(
                        onClick = onProceedToPayment,
                        modifier = Modifier.weight(1.2f).height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Select Payment", fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                BillSummaryCard(
                    subtotal    = cartSummary.itemsSubtotal,
                    delivery    = cartSummary.deliveryFee,
                    platformFee = cartSummary.platformFee,
                    gst         = cartSummary.taxTotal,
                    discount    = cartSummary.discount,
                    total       = cartSummary.finalTotal
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryGreen)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Delivery Address", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Village Halli, Karnataka", fontSize = 12.sp, color = TextSecondary)
                        }
                    }
                }
            }
        }
    }
}
