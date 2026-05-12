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
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.repository.OrderRepository
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerOrdersScreen(
    onBack: () -> Unit,
    onTrackOrder: (String) -> Unit
) {
    val repository = remember { OrderRepository() }
    val userId = FirebaseManager.auth.currentUser?.uid ?: ""
    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            repository.getOrdersForBuyer(userId).collectLatest {
                orders = it
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders", fontWeight = FontWeight.Bold, color = PrimaryGreenDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryGreenDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
        } else if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ReceiptLong, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("You haven't placed any orders yet", color = TextSecondary)
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
                items(orders) { order ->
                    BuyerOrderCard(order, onTrackOrder)
                }
            }
        }
    }
}

@Composable
fun BuyerOrderCard(order: Order, onTrackOrder: (String) -> Unit) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    val dateString = dateFormat.format(order.createdAt.toDate())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Order #${order.orderId}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryGreenDark)
                    Text(dateString, fontSize = 12.sp, color = TextSecondary)
                }
                StatusBadge(order.orderStatus)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = BackgroundLight.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))
            
            order.items.forEach { item ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${item.quantity}x ${item.name}", fontSize = 14.sp, color = TextPrimary)
                    Text("₹${(item.price * item.quantity).toInt()}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = BackgroundLight.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Total Amount", fontSize = 12.sp, color = TextSecondary)
                    Text("₹${order.finalAmount.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryGreen)
                }
                Button(
                    onClick = { onTrackOrder(order.orderId) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Track", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
