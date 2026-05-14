package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.components.OrderTrackingTimeline
import com.hallisanthe.app.viewmodel.OrdersViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerTrackingScreen(
    orderId: String,
    ordersViewModel: OrdersViewModel,
    onBack: () -> Unit
) {
    val currentOrder by ordersViewModel.currentTrackingOrder.collectAsState()

    LaunchedEffect(orderId) {
        ordersViewModel.startTracking(orderId)
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9F4EE))
            )
        },
        containerColor = Color(0xFFF9F4EE)
    ) { paddingValues ->
        if (currentOrder == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF2E7D32))
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
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Order ID: #${order.orderId.takeLast(6).uppercase()}", fontWeight = FontWeight.Bold, color = Color(0xFF1B3A2D), fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Placed on: ${sdf.format(order.createdAt.toDate())}", color = Color.Gray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Delivery: ${order.deliveryType}", color = Color.Gray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Estimated Arrival: ${order.estimatedTime}", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Total Paid: ₹${order.finalAmount.toInt()}", fontWeight = FontWeight.ExtraBold, color = Color(0xFF1B3A2D), fontSize = 16.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if (order.orderStatus == "CANCELLED" || order.orderStatus == "REJECTED") {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                    ) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Order ${order.orderStatus}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Red)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("This order was not accepted or was cancelled.", color = Color.Red, fontSize = 14.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        }
                    }
                } else {
                    OrderTrackingTimeline(currentStatus = order.orderStatus)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Delivery Address Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Delivery Address", fontWeight = FontWeight.Bold, color = Color(0xFF1B3A2D), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(order.deliveryAddress.ifEmpty { "Main Street, Halli Village" }, color = Color.Gray, fontSize = 13.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
