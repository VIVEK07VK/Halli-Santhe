package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hallisanthe.app.models.CartItem
import com.hallisanthe.app.ui.components.BillSummaryCard
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onCheckout: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartSummary by cartViewModel.cartSummary.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                CartBottomBar(
                    total = cartSummary.finalTotal,
                    onCheckout = onCheckout
                )
            }
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("Your cart is empty", color = TextSecondary, fontSize = 16.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartItems, key = { it.productId }) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = { cartViewModel.updateQuantity(item.productId, item.quantity + 1) },
                        onDecrease = { cartViewModel.updateQuantity(item.productId, item.quantity - 1) },
                        onRemove = { cartViewModel.removeItem(item.productId) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    BillSummaryCard(
                        subtotal    = cartSummary.itemsSubtotal,
                        delivery    = cartSummary.deliveryFee,
                        platformFee = cartSummary.platformFee,
                        gst         = cartSummary.taxTotal,
                        total       = cartSummary.finalTotal,
                        discount    = cartSummary.discount
                    )
                    Spacer(modifier = Modifier.height(80.dp)) // Extra padding for bottom bar
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PrimaryGreenDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹${item.price.toInt()} / ${item.unit}",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "₹${(item.price * item.quantity).toInt()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = DiscountRed
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.DeleteOutline, contentDescription = "Remove", tint = TextSecondary)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(BackgroundLight, RoundedCornerShape(8.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    IconButton(onClick = onDecrease, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                    }
                    Text(
                        text = item.quantity.toString(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = onIncrease, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun CartBottomBar(total: Double, onCheckout: () -> Unit) {
    Surface(
        color = SurfaceLight,
        shadowElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Total", color = TextSecondary, fontSize = 12.sp)
                Text(
                    "₹${String.format("%.2f", total)}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = PrimaryGreenDark
                )
            }
            Button(
                onClick = onCheckout,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryOrange)
            ) {
                Text("Proceed to Checkout", fontWeight = FontWeight.Bold, color = SurfaceLight)
            }
        }
    }
}
