package com.hallisanthe.app.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.components.StatusBadge
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.CartViewModel

@Composable
fun OrderConfirmationScreen(
    orderId: String,
    cartViewModel: CartViewModel,
    onBackToHome: () -> Unit,
    onTrackOrder: () -> Unit
) {
    // Read the payment method stored in the ViewModel's last order
    // (passed via the nav argument; we determine COD by checking orderId prefix is same)
    // The paymentMethod is passed from BuyerNavigation via the route; for simplicity
    // we store it as a saved state in CartViewModel after checkout.
    val paymentMethod by cartViewModel.lastPaymentMethod.collectAsState()
    val isCod = paymentMethod == "COD"

    LaunchedEffect(Unit) {
        cartViewModel.clearCart()
    }

    // Pulsing scale animation for the success icon
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue   = 1f,
        targetValue    = 1.08f,
        animationSpec  = infiniteRepeatable(
            animation  = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // ── Success / COD Icon ──────────────────────────────
        Box(
            modifier = Modifier
                .size(110.dp)
                .scale(scale)
                .background(
                    brush = Brush.radialGradient(
                        colors = if (isCod)
                            listOf(SecondaryOrange.copy(alpha = 0.3f), SecondaryOrange.copy(alpha = 0.05f))
                        else
                            listOf(PrimaryGreen.copy(alpha = 0.3f), PrimaryGreen.copy(alpha = 0.05f))
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .background(
                        color = if (isCod) SecondaryOrange else PrimaryGreen,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = if (isCod) Icons.Default.Payments else Icons.Default.Check,
                    contentDescription = if (isCod) "COD" else "Success",
                    tint               = SurfaceLight,
                    modifier           = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── Title ───────────────────────────────────────────
        Text(
            text       = if (isCod) "Order Placed!" else "Payment Successful!",
            fontSize   = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color      = if (isCod) SecondaryOrange else PrimaryGreenDark
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ── Subtitle ────────────────────────────────────────
        Text(
            text      = if (isCod)
                "Your order has been placed with Cash on Delivery. Please keep ₹ ready when the delivery arrives."
            else
                "Your order has been placed successfully. Thank you for supporting local artisans!",
            fontSize  = 15.sp,
            color     = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── Order ID Card ───────────────────────────────────
        Card(
            modifier  = Modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(16.dp),
            colors    = CardDefaults.cardColors(containerColor = SurfaceLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier            = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Order ID", color = TextSecondary, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    orderId,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 20.sp,
                    color      = PrimaryGreenDark
                )
                if (isCod) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = SecondaryOrange.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "💵  Pay on Delivery",
                            color      = SecondaryOrange,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 13.sp,
                            modifier   = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = PrimaryGreen.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "✅  Payment Confirmed",
                            color      = PrimaryGreenDark,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 13.sp,
                            modifier   = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // ── Track Order button ──────────────────────────────
        Button(
            onClick  = onTrackOrder,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape  = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text("Track Order", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SurfaceLight)
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ── Back to Home button ─────────────────────────────
        OutlinedButton(
            onClick  = onBackToHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape  = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = SecondaryOrange),
            border = BorderStroke(1.5.dp, SecondaryOrange)
        ) {
            Text("Back to Home", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
