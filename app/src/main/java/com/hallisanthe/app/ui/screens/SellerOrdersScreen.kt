package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.Order
import com.hallisanthe.app.ui.components.StatusBadge
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.SellerViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerOrdersScreen(
    sellerViewModel: SellerViewModel,
    onBack: () -> Unit
) {
    val orders by sellerViewModel.sellerOrders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order History", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ReceiptLong, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No orders found", color = TextSecondary)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders.sortedByDescending { it.createdAt.seconds }) { order ->
                    SellerOrderCard(order)
                }
            }
        }
    }
}

@Composable
fun SellerOrderCard(order: Order) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()) }
    val dateString = dateFormat.format(order.createdAt.toDate())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("#${order.orderId}", fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                StatusBadge(order.orderStatus)
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = BackgroundLight)
            Spacer(modifier = Modifier.height(8.dp))
            
            order.items.forEach { item ->
                Text("${item.quantity}x ${item.name}", fontSize = 14.sp)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Date: $dateString", fontSize = 12.sp, color = TextSecondary)
                    Text("Method: ${order.paymentMethod}", fontSize = 12.sp, color = TextSecondary)
                }
                Text("₹${order.totalAmount.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryGreenDark)
            }
        }
    }
}

