package com.hallisanthe.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.Order
import com.hallisanthe.app.models.OrderStatus
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SellerOrderCard(
    order: Order,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onPreparing: () -> Unit,
    onReady: () -> Unit,
    onOutForDelivery: () -> Unit,
    onDelivered: () -> Unit
) {
    val status = try { OrderStatus.valueOf(order.orderStatus) } catch (e: Exception) { OrderStatus.PENDING }
    val date = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(order.createdAt.toDate())

    val statusColor = when (status) {
        OrderStatus.PENDING -> Color(0xFFFF9800)
        OrderStatus.ACCEPTED -> Color(0xFF2196F3)
        OrderStatus.PREPARING -> Color(0xFF3F51B5)
        OrderStatus.READY_FOR_PICKUP -> Color(0xFF9C27B0)
        OrderStatus.OUT_FOR_DELIVERY -> Color(0xFF673AB7)
        OrderStatus.DELIVERED -> Color(0xFF4CAF50)
        OrderStatus.CANCELLED, OrderStatus.REJECTED -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Order #${order.orderId.takeLast(6).uppercase()}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(date, fontSize = 12.sp, color = Color.Gray)
                }
                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        order.orderStatus.replace("_", " "),
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text(order.buyerName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }

            Spacer(Modifier.height(8.dp))

            order.items.forEach { item ->
                Text("${item.quantity}x ${item.name}", fontSize = 13.sp, color = Color.DarkGray)
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Amount", fontSize = 11.sp, color = Color.Gray)
                    Text("₹${order.finalAmount.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFF2E7D32))
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (order.deliveryType == "PICKUP") Icons.Default.Storefront else Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(order.deliveryType, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (status) {
                    OrderStatus.PENDING -> {
                        Button(
                            onClick = onReject,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color.Red),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Reject", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = onAccept,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Accept", fontWeight = FontWeight.Bold)
                        }
                    }
                    OrderStatus.ACCEPTED -> {
                        Button(
                            onClick = onPreparing,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Mark Preparing", fontWeight = FontWeight.Bold)
                        }
                    }
                    OrderStatus.PREPARING -> {
                        Button(
                            onClick = onReady,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (order.deliveryType == "PICKUP") "Ready for Pickup" else "Mark Ready", fontWeight = FontWeight.Bold)
                        }
                    }
                    OrderStatus.READY_FOR_PICKUP -> {
                        if (order.deliveryType == "DELIVERY") {
                            Button(
                                onClick = onOutForDelivery,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Out for Delivery", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Button(
                                onClick = onDelivered,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Mark Picked Up", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    OrderStatus.OUT_FOR_DELIVERY -> {
                        Button(
                            onClick = onDelivered,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Mark Delivered", fontWeight = FontWeight.Bold)
                        }
                    }
                    else -> {
                        // Order completed or cancelled - show details button maybe
                        OutlinedButton(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE))
                        ) {
                            Text("View Details", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
