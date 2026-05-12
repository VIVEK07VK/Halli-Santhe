package com.hallisanthe.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.OrderStatus
import com.hallisanthe.app.ui.theme.PrimaryGreen
import com.hallisanthe.app.ui.theme.SecondaryOrange
import com.hallisanthe.app.ui.theme.SurfaceLight

@Composable
fun OrderTimelineComponent(currentStatus: String) {
    val statuses = OrderStatus.values().filter { it != OrderStatus.CANCELLED }
    val currentIndex = statuses.indexOfFirst { it.name == currentStatus }

    Column(modifier = Modifier.padding(16.dp)) {
        statuses.forEachIndexed { index, status ->
            val isCompleted = index <= currentIndex
            val isCurrent = index == currentIndex
            
            Row(verticalAlignment = Alignment.Top) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCurrent -> SecondaryOrange
                                    isCompleted -> PrimaryGreen
                                    else -> Color.LightGray
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isCompleted && !isCurrent) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = SurfaceLight, modifier = Modifier.size(16.dp))
                        } else if (isCurrent) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = SurfaceLight, modifier = Modifier.size(16.dp))
                        } else {
                            Icon(Icons.Default.Circle, contentDescription = null, tint = SurfaceLight, modifier = Modifier.size(8.dp))
                        }
                    }
                    if (index < statuses.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(40.dp)
                                .background(if (index < currentIndex) PrimaryGreen else Color.LightGray)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = status.name.replace("_", " "),
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        color = if (isCurrent) SecondaryOrange else if (isCompleted) PrimaryGreen else Color.Gray,
                        fontSize = 16.sp
                    )
                    if (isCurrent) {
                        Text(
                            text = getStatusDescription(status),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

private fun getStatusDescription(status: OrderStatus): String {
    return when (status) {
        OrderStatus.PENDING -> "Your order has been received."
        OrderStatus.WAITING_CONFIRMATION -> "Waiting for seller to confirm."
        OrderStatus.ACCEPTED -> "Seller has accepted your order."
        OrderStatus.PREPARING -> "Your order is being prepared."
        OrderStatus.PACKED -> "Order is packed and ready."
        OrderStatus.OUT_FOR_DELIVERY -> "Out for delivery. Arriving soon!"
        OrderStatus.READY_FOR_PICKUP -> "Ready for pickup at the store."
        OrderStatus.DELIVERED -> "Order delivered successfully."
        OrderStatus.CANCELLED -> "Order was cancelled."
    }
}
