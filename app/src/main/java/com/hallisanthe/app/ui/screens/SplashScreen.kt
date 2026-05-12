package com.hallisanthe.app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Terra-cotta palette matching the design
private val TerraCotta   = Color(0xFFC0533A)
private val IconCircle   = Color(0xFFE8886A)
private val IconBg       = Color(0xFFD4715A)
private val White        = Color(0xFFFFFFFF)
private val WhiteFaded   = Color(0xCCFFFFFF)

@Composable
fun SplashScreen(onExploreClicked: () -> Unit) {
    // Fade-in animation
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "fadeIn"
    )
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.85f,
        animationSpec = tween(durationMillis = 900),
        label = "scaleIn"
    )

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TerraCotta),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha)
                .scale(scale),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Shop icon inside nested circles
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(IconCircle),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(IconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Store,
                        contentDescription = "Marketplace",
                        tint = TerraCotta,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // "Halli-Santhe" in bold italic serif
            Text(
                text = "Halli-Santhe",
                color = White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle in spaced uppercase
            Text(
                text = "HYPERLOCAL ARTISAN MARKETPLACE",
                color = WhiteFaded,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.5.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(64.dp))

            // "Explore Village Goods" button — underlined text style
            var btnPressed by remember { mutableStateOf(false) }
            val btnAlpha by animateFloatAsState(
                targetValue = if (btnPressed) 0.6f else 1f,
                animationSpec = tween(100),
                label = "btnAlpha"
            )
            Text(
                text = "Explore Village Goods",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .alpha(btnAlpha)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onExploreClicked()
                    }
                    .padding(vertical = 8.dp, horizontal = 24.dp)
            )
        }
    }
}
