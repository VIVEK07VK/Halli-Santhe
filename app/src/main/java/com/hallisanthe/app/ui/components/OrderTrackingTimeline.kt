package com.hallisanthe.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.OrderStatus

@Composable
fun OrderTrackingTimeline(currentStatus: String) {
    val status = try { OrderStatus.valueOf(currentStatus) } catch (e: Exception) { OrderStatus.PENDING }

    // If order is cancelled or rejected, the timeline shouldn't show progress past PENDING
    val isTerminated = status == OrderStatus.CANCELLED || status == OrderStatus.REJECTED

    val steps = listOf(
        "Order Placed" to OrderStatus.PENDING,
        "Confirmed" to OrderStatus.ACCEPTED,
        "Preparing" to OrderStatus.PREPARING,
        "Out for Delivery" to OrderStatus.OUT_FOR_DELIVERY,
        "Delivered" to OrderStatus.DELIVERED
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Text("Order Progress", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1B3A2D))
        Spacer(Modifier.height(24.dp))

        steps.forEachIndexed { index, step ->
            val stepStatus = step.second
            
            val isCompleted = when {
                isTerminated -> stepStatus == OrderStatus.PENDING
                else -> status.ordinal >= stepStatus.ordinal
            }
            
            val isActive = !isTerminated && status == stepStatus

            TimelineStep(
                title = step.first,
                isCompleted = isCompleted,
                isActive = isActive,
                isLast = index == steps.size - 1
            )
        }
    }
}

@Composable
fun TimelineStep(
    title: String,
    isCompleted: Boolean,
    isActive: Boolean,
    isLast: Boolean
) {
    val color by animateColorAsState(
        targetValue = if (isCompleted) Color(0xFF2E7D32) else Color(0xFFE0E0E0),
        label = "timelineColor"
    )

    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(if (isActive) 24.dp else 16.dp)
                    .background(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .weight(1f)
                        .background(color.copy(alpha = 0.5f))
                )
            }
        }
        
        Spacer(Modifier.width(16.dp))
        
        Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 24.dp)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                color = if (isCompleted) Color(0xFF1B3A2D) else Color.Gray
            )
            if (isActive) {
                Text(
                    "Current Status",
                    fontSize = 10.sp,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
