package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hallisanthe.app.models.RecentlyViewedProduct
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentlyViewedScreen(
    profileViewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val recentProducts by profileViewModel.recentlyViewed.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recently Viewed", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (recentProducts.isNotEmpty()) {
                        IconButton(onClick = { profileViewModel.clearHistory() }) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear History")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (recentProducts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No viewing history", color = TextSecondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recentProducts) { product ->
                    RecentProductCard(product)
                }
            }
        }
    }
}

@Composable
fun RecentProductCard(product: RecentlyViewedProduct) {
    val dateFormat = remember { SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()) }
    val dateString = dateFormat.format(Date(product.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(product.category, fontSize = 13.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Viewed: $dateString", fontSize = 11.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("₹${product.price.toInt()}", fontWeight = FontWeight.ExtraBold, color = PrimaryGreenDark)
        }
    }
}
