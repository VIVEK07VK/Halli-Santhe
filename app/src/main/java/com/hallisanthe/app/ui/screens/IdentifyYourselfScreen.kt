package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color palette matching the design
private val CreamBg        = Color(0xFFF0EBE1)
private val DarkTeal       = Color(0xFF1E3A3A)
private val CardWhite      = Color(0xFFFFFFFF)
private val CardDark       = Color(0xFF1E3A3A)
private val AccentOrange   = Color(0xFFD05A3A)
private val SubtitleGray   = Color(0xFF8A8A8A)
private val SmallLabelGray = Color(0xFF9E9E9E)
private val IconBgLight    = Color(0xFFF5E8D8)
private val IconBgDark     = Color(0xFF2C5050)

@Composable
fun IdentifyYourselfScreen(
    onBuyerSelected: () -> Unit,
    onSellerSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),  // outer black as in screenshot
        contentAlignment = Alignment.Center
    ) {
        // Card-like inner container (phone frame effect)
        Box(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .clip(RoundedCornerShape(28.dp))
                .background(CreamBg)
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                // Heading
                Text(
                    text = "Identify Yourself",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = DarkTeal,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "Continue as a buyer or start selling your village crafts.",
                    fontSize = 14.sp,
                    color = SubtitleGray,
                    fontFamily = FontFamily.SansSerif,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Buyer card (light)
                RoleCard(
                    title = "I am a Buyer",
                    subtitle = "FIND AUTHENTIC\nLOCAL GOODS",
                    icon = Icons.Filled.ShoppingBag,
                    isDark = false,
                    onClick = onBuyerSelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Seller card (dark)
                RoleCard(
                    title = "I am a Seller",
                    subtitle = "LIST YOUR VILLAGE\nPRODUCTS",
                    icon = Icons.Filled.Store,
                    isDark = true,
                    onClick = onSellerSelected
                )
            }
        }
    }
}

@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isDark: Boolean,
    onClick: () -> Unit
) {
    val bgColor       = if (isDark) CardDark else CardWhite
    val titleColor    = if (isDark) Color.White else DarkTeal
    val subtitleColor = if (isDark) Color(0xFF8AACAC) else SmallLabelGray
    val iconBg        = if (isDark) IconBgDark else IconBgLight
    val arrowColor    = if (isDark) Color(0xFF6A9A9A) else Color(0xFFCCCCCC)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 18.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon circle
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = AccentOrange,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text block
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = titleColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                color = subtitleColor,
                letterSpacing = 0.8.sp,
                lineHeight = 15.sp
            )
        }

        // Chevron arrow
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Navigate",
            tint = arrowColor,
            modifier = Modifier.size(22.dp)
        )
    }
}
