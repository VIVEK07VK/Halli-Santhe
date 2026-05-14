package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hallisanthe.app.models.OrderStatus
import com.hallisanthe.app.ui.components.SellerOrderCard
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.OrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageOrdersScreen(
    onBack: () -> Unit,
    ordersViewModel: OrdersViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val sessionUser by authViewModel.sessionUser.collectAsState()
    val orders by ordersViewModel.sellerOrders.collectAsState()
    val isLoading by ordersViewModel.isLoading.collectAsState()

    LaunchedEffect(sessionUser) {
        sessionUser?.uid?.let { sellerId ->
            ordersViewModel.listenToSellerOrders(sellerId)
        }
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Pending", "Active", "Completed")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Orders", fontWeight = FontWeight.Bold, color = Color(0xFF1B3A2D)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1B3A2D))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9F4EE))
            )
        },
        containerColor = Color(0xFFF9F4EE)
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFF2E7D32),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFF2E7D32)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 14.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            val filteredOrders = when (selectedTab) {
                0 -> orders.filter { it.orderStatus == OrderStatus.PENDING.name }
                1 -> orders.filter { 
                    it.orderStatus != OrderStatus.PENDING.name && 
                    it.orderStatus != OrderStatus.DELIVERED.name && 
                    it.orderStatus != OrderStatus.CANCELLED.name &&
                    it.orderStatus != OrderStatus.REJECTED.name
                }
                else -> orders.filter { 
                    it.orderStatus == OrderStatus.DELIVERED.name || 
                    it.orderStatus == OrderStatus.CANCELLED.name ||
                    it.orderStatus == OrderStatus.REJECTED.name
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF2E7D32))
                }
            } else if (filteredOrders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No orders found", color = Color.Gray, fontSize = 16.sp)
                        Text("Check other tabs or wait for new requests", color = Color.LightGray, fontSize = 12.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(filteredOrders) { order ->
                        SellerOrderCard(
                            order = order,
                            onAccept = { ordersViewModel.acceptOrder(order.orderId) },
                            onReject = { ordersViewModel.rejectOrder(order.orderId) },
                            onPreparing = { ordersViewModel.markPreparing(order.orderId) },
                            onReady = { ordersViewModel.markReady(order.orderId) },
                            onOutForDelivery = { ordersViewModel.markOutForDelivery(order.orderId) },
                            onDelivered = { ordersViewModel.markDelivered(order.orderId) }
                        )
                    }
                }
            }
        }
    }
}
