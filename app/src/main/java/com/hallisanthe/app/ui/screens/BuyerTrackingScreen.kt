package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.components.OrderTimelineComponent
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.OrderTrackingViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerTrackingScreen(
    orderId: String,
    trackingViewModel: OrderTrackingViewModel,
    onBack: () -> Unit
) {
    val currentOrder by trackingViewModel.currentOrder.collectAsState()

    LaunchedEffect(orderId) {
        trackingViewModel.trackOrder(orderId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Track Order", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        if (currentOrder == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator(color = SecondaryOrange)
            }
        } else {
            val order = currentOrder!!
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Order ID: ${order.orderId.takeLast(6)}", fontWeight = FontWeight.Bold, color = PrimaryGreenDark, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Placed on: ${sdf.format(order.createdAt.toDate())}", color = TextSecondary, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Delivery: ${if (order.deliveryType == "SELF_PICKUP") "Self Pickup" else "Local Seller Delivery"}", color = TextSecondary, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Estimated Time: ${order.estimatedTime}", color = PrimaryGreenDark, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Total: ₹${order.finalAmount.toInt()}", fontWeight = FontWeight.Bold, color = DiscountRed)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if (order.orderStatus == "CANCELLED") {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = DiscountRed.copy(alpha = 0.1f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = DiscountRed, modifier = Modifier.size(48.dp)) // use warning or cancel icon if available
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Order Cancelled", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DiscountRed)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Seller rejected this order.", color = DiscountRed, fontSize = 14.sp)
                        }
                    }
                } else {
                    Text("Live Tracking", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        OrderTimelineComponent(currentStatus = order.orderStatus)
                    }
                }
            }
        }
    }
}
