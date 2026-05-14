package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.EarningsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarningsHistoryScreen(
    onBack: () -> Unit,
    earningsViewModel: EarningsViewModel = viewModel()
) {
    val earningsSummary by earningsViewModel.earningsSummary.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Earnings History", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF9F4EE)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Summary Cards
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    EarningsStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Total Sales",
                        amount = "₹${earningsSummary.totalSales.toInt()}",
                        color = PrimaryGreen
                    )
                    EarningsStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Seller Revenue",
                        amount = "₹${earningsSummary.totalRevenue.toInt()}",
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    EarningsStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Commission Paid",
                        amount = "₹${earningsSummary.totalCommissionDeducted.toInt()}",
                        color = DiscountRed
                    )
                    EarningsStatCard(
                        modifier = Modifier.weight(1f),
                        title = "Today",
                        amount = "₹${earningsSummary.todayEarnings.toInt()}",
                        color = SecondaryOrange
                    )
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Text("Recent Completed Payouts", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryGreenDark)
            }

            // Placeholder for payout list
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.History, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(48.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("No completed payouts yet", color = Color.Gray, fontSize = 14.sp)
                        Text("Payouts are processed every Monday", color = Color.Gray.copy(alpha = 0.6f), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun EarningsStatCard(modifier: Modifier, title: String, amount: String, color: Color) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Text(amount, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = color)
        }
    }
}
