package com.hallisanthe.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.hallisanthe.app.utils.UpiApp

@Composable
fun UpiAppRow(
    upiApp: UpiApp,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFF9F4EE)),
            contentAlignment = Alignment.Center
        ) {
            if (upiApp.icon != null) {
                Image(
                    bitmap = upiApp.icon.toBitmap().asImageBitmap(),
                    contentDescription = upiApp.name,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                val emoji = when {
                    upiApp.name.contains("Google", true) -> "G"
                    upiApp.name.contains("PhonePe", true) -> "P"
                    upiApp.name.contains("Paytm", true) -> "Py"
                    else -> "U"
                }
                Text(emoji, fontWeight = FontWeight.Black, color = Color(0xFF2E7D32))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                upiApp.name, 
                fontWeight = FontWeight.Medium, 
                color = Color(0xFF1B3A2D), 
                fontSize = 14.sp
            )
        }
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFE67E22))
        )
    }
}

@Composable
fun UpiAppSelector(
    installedApps: List<UpiApp>,
    selectedApp: UpiApp?,
    onAppSelected: (UpiApp) -> Unit
) {
    if (installedApps.isEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Box(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No UPI apps found. Please install GPay, PhonePe or Paytm.",
                    color = Color.Red.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }
    } else {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                installedApps.forEach { app ->
                    UpiAppRow(
                        upiApp = app,
                        isSelected = selectedApp?.packageName == app.packageName,
                        onClick = { onAppSelected(app) }
                    )
                }
            }
        }
    }
}
