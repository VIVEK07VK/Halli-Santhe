package com.hallisanthe.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.Order
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.OrdersViewModel
import kotlinx.coroutines.delay

@Composable
fun OrderConfirmationScreen(
    orderId: String,
    ordersViewModel: OrdersViewModel,
    onBackToHome: () -> Unit,
    onTrackOrder: () -> Unit
) {
    val order by ordersViewModel.currentTrackingOrder.collectAsState()
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(orderId) {
        ordersViewModel.startTracking(orderId)
        delay(200) // Small entrance delay
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // --- Top Decorative Bar (Matching Reference Image Teal) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFF236E60)) // Reference Teal
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 100.dp, bottom = 40.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { it / 2 }
                ) {
                    MainConfirmationCard(
                        order = order,
                        onTrackOrder = onTrackOrder,
                        onContinueShopping = onBackToHome
                    )
                }
            }

            if (order != null) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn(tween(800, delayMillis = 200)) + slideInVertically(tween(800, delayMillis = 200)) { it / 4 }
                    ) {
                        OrderSummaryDetailsCard(order!!)
                    }
                }
            }
        }
    }
}

@Composable
fun MainConfirmationCard(
    order: Order?,
    onTrackOrder: () -> Unit,
    onContinueShopping: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .shadow(30.dp, RoundedCornerShape(40.dp), spotColor = Color.Black.copy(0.12f)),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 48.dp, horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Success Animation ---
            SuccessCheckmarkAnimation()

            Spacer(modifier = Modifier.height(36.dp))

            // --- Title ---
            Text(
                text = "Order Placed!",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF1B3A2D)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Message ---
            Text(
                text = if (order?.paymentMethod == "COD") 
                    "Order #${order.orderId.takeLast(6)} is confirmed via Cash on Delivery. Your support helps local artisans thrive."
                    else "Payment successful. Your order #${order?.orderId?.takeLast(6) ?: "..."} is confirmed and being prepared by the artisan.",
                fontSize = 15.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Status Box (High fidelity match) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF3F1E7)) // Light Beige/Cream status box
                    .padding(vertical = 20.dp, horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "STATUS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA6A495), // Muted status label
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = when(order?.orderStatus) {
                            "PENDING" -> "Waiting for seller confirmation"
                            "ACCEPTED" -> "Order Accepted"
                            else -> "Processing your order"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF234439),
                        textAlign = TextAlign.Center
                    )
                    if (order?.estimatedTime != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Est. Delivery: ${order.estimatedTime}",
                            fontSize = 13.sp,
                            color = Color(0xFF236E60),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // --- Track Order Button (Burnt Orange/Terracotta Match) ---
            Button(
                onClick = onTrackOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC04036)) // DiscountRed/Terracotta
            ) {
                Text("Track Order", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Continue Shopping Button ---
            TextButton(onClick = onContinueShopping) {
                Text(
                    "CONTINUE SHOPPING",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFC04036),
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
fun OrderSummaryDetailsCard(order: Order) {
    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(28.dp)) {
            Text("Payment Details", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1B3A2D))
            Spacer(modifier = Modifier.height(20.dp))
            
            SummaryRow("Item Total", "₹${String.format("%.2f", order.subtotal)}")
            SummaryRow("Delivery Fee", "₹${String.format("%.2f", order.deliveryFee)}")
            SummaryRow("Platform Fee", "₹${String.format("%.2f", order.platformFee)}")
            SummaryRow("GST/Tax", "₹${String.format("%.2f", order.gst)}")
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFF3F1E7))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Total Paid", color = TextSecondary, fontSize = 12.sp)
                    Text("via ${order.paymentMethod}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF236E60))
                }
                Text("₹${String.format("%.2f", order.finalAmount)}", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFF1B3A2D))
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1B3A2D))
    }
}

@Composable
fun SuccessCheckmarkAnimation() {
    var animate by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animate) 1f else 0f,
        animationSpec = tween(800, easing = LinearOutSlowInEasing),
        label = "progress"
    )
    
    val scale by animateFloatAsState(
        targetValue = if (animate) 1f else 0.5f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessLow),
        label = "scale"
    )

    LaunchedEffect(Unit) {
        delay(100)
        animate = true
    }

    Box(
        modifier = Modifier
            .size(110.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(Color(0xFFD9F4ED)), // Soft mint background
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(50.dp)) {
            val path = Path().apply {
                moveTo(size.width * 0.15f, size.height * 0.5f)
                lineTo(size.width * 0.45f, size.height * 0.8f)
                lineTo(size.width * 0.85f, size.height * 0.2f)
            }
            
            val pathMeasure = android.graphics.PathMeasure(path.asAndroidPath(), false)
            val length = pathMeasure.length
            
            drawPath(
                path = path,
                color = Color(0xFF236E60),
                style = Stroke(
                    width = 8.dp.toPx(), 
                    cap = StrokeCap.Round,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                        floatArrayOf(length, length),
                        (1f - progress) * length
                    )
                )
            )
        }
    }
}
