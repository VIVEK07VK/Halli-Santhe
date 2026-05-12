package com.hallisanthe.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Color palette
private val GBg         = Color(0xFFF9F4EE)
private val GDarkTeal   = Color(0xFF1B3A2D)
private val GCardWhite  = Color(0xFFFFFFFF)
private val GCardDark   = Color(0xFF1B3A2D)
private val GAccentOrange = Color(0xFFE65100)
private val GSubGray    = Color(0xFF6B7280)
private val GIconBgLight= Color(0xFFF5E8D8)
private val GIconBgDark = Color(0xFF2C5050)
private val GGreenAccent= Color(0xFF2E7D32)

@Composable
fun RoleSelectionScreen(
    onBuyerSelected: () -> Unit,
    onSellerSelected: () -> Unit
) {
    // Entrance animation
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(700),
        label = "roleAlpha"
    )
    val slideY by animateFloatAsState(
        targetValue = if (visible) 0f else 60f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "roleSlide"
    )
    LaunchedEffect(Unit) { delay(150); visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1B3A2D), Color(0xFF2E7D32), Color(0xFF1B5E20))
                )
            )
    ) {
        // Floating decorative emojis (background layer)
        FloatingEmoji("🌾", 0.1f, 0.02f, 22)
        FloatingEmoji("🥭", 0.9f, 0.05f, 20)
        FloatingEmoji("🧺", 0.1f, 0.7f, 20)
        FloatingEmoji("🏺", 0.85f, 0.75f, 18)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .offset(y = slideY.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))

            // Logo area
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text("🛒", fontSize = 44.sp)
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Halli-Santhe",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif
            )
            Text(
                "DIGITAL MARKETPLACE",
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 10.sp,
                letterSpacing = 3.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.weight(1f))

            // Bottom sheet card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = GBg),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    Text(
                        "Who are you?",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = GDarkTeal
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Choose your role to get started on the village marketplace",
                        fontSize = 13.sp,
                        color = GSubGray,
                        lineHeight = 18.sp
                    )

                    Spacer(Modifier.height(28.dp))

                    RoleCard(
                        title = "I'm a Buyer",
                        subtitle = "BROWSE LOCAL PRODUCE & CRAFTS",
                        emoji = "🛍️",
                        isDark = false,
                        accent = GGreenAccent,
                        onClick = onBuyerSelected
                    )

                    Spacer(Modifier.height(14.dp))

                    RoleCard(
                        title = "I'm a Seller",
                        subtitle = "LIST VILLAGE GOODS & ARTISAN CRAFTS",
                        emoji = "🏪",
                        isDark = true,
                        accent = GAccentOrange,
                        onClick = onSellerSelected
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        "By continuing you agree to our Terms & Privacy Policy",
                        color = GSubGray,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    emoji: String,
    isDark: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    val bgColor = if (isDark) GCardDark else GCardWhite
    val titleColor = if (isDark) Color.White else GDarkTeal
    val subColor = if (isDark) Color(0xFF80CBC4) else GSubGray
    val arrowColor = if (isDark) Color(0xFF80CBC4) else Color(0xFFBDBDBD)

    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
        label = "roleCardScale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(if (isDark) 8.dp else 4.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .clickable {
                pressed = true
                onClick()
            }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Emoji in circle
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = if (isDark) 0.25f else 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 26.sp)
        }
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = titleColor)
            Spacer(Modifier.height(3.dp))
            Text(
                subtitle, fontSize = 10.sp, fontWeight = FontWeight.Medium,
                color = subColor, letterSpacing = 0.8.sp, lineHeight = 14.sp
            )
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = arrowColor, modifier = Modifier.size(24.dp))
    }
}
