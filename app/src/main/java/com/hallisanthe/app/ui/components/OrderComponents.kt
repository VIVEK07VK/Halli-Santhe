package com.hallisanthe.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*

@Composable
fun StatusBadge(status: String) {
    val color = when (status.uppercase()) {
        "DELIVERED" -> PrimaryGreen
        "CANCELLED" -> DiscountRed
        "ACCEPTED", "PREPARING", "PACKED", "OUT_FOR_DELIVERY" -> PrimaryGreenDark
        else -> SecondaryOrange
    }
    Surface(
        color = color.copy(alpha = 0.12f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            status,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun BillSummaryCard(
    subtotal: Double,
    delivery: Double,
    platformFee: Double,
    gst: Double,
    total: Double,
    discount: Double = 0.0
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = SurfaceLight),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Bill Summary", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PrimaryGreenDark)
            Spacer(modifier = Modifier.height(12.dp))
            
            BillRow("Item Total",             subtotal,    Color.Unspecified)
            BillRow("GST (Tax)",              gst,         Color.Unspecified)
            BillRow("Delivery Fee",           delivery,    Color.Unspecified)
            BillRow("Platform Fee",           platformFee, Color.Unspecified)
            
            if (discount > 0) {
                BillRow("Discount", -discount, PrimaryGreen)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = BackgroundLight, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("To Pay", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryGreenDark)
                Text("₹${String.format("%.2f", total)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PrimaryGreenDark)
            }
        }
    }
}

@Composable
fun BillRow(label: String, amount: Double, color: Color = Color.Unspecified) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = TextSecondary)
        Text(
            text  = if (amount < 0) "-₹${String.format("%.2f", -amount)}" else "₹${String.format("%.2f", amount)}",
            fontSize = 14.sp,
            color = if (color == Color.Unspecified) PrimaryGreenDark else color,
            fontWeight = FontWeight.Medium
        )
    }
}
