package com.hallisanthe.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.viewmodel.AuthUiState
import com.hallisanthe.app.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

/**
 * LoadingScreen – shown during the session check on cold launch.
 * It auto-advances once the ViewModel resolves the session state.
 */
@Composable
fun LoadingScreen(
    authViewModel: AuthViewModel,
    onNavigateToBuyerHome: () -> Unit,
    onNavigateToSellerDashboard: () -> Unit,
    onNavigateToRoleSelection: () -> Unit
) {
    val uiState by authViewModel.uiState.collectAsState()

    // Trigger session check once
    LaunchedEffect(Unit) {
        authViewModel.checkSession()
    }

    // React to state changes
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.Success -> {
                delay(600) // Small pause so animation completes
                val role = state.user?.role
                if (role == UserRole.SELLER.name) {
                    onNavigateToSellerDashboard()
                } else {
                    onNavigateToBuyerHome()
                }
            }
            is AuthUiState.Unauthenticated -> {
                delay(600)
                onNavigateToRoleSelection()
            }
            else -> Unit
        }
    }

    // Animations
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing)),
        label = "spin"
    )
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(700),
        label = "fadeIn"
    )
    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1B3A2D), Color(0xFF2E7D32), Color(0xFF1B5E20))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha)
        ) {
            // Spinning market emblem
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .rotate(rotation)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .scale(pulse)
                        .clip(CircleShape)
                        .background(Color(0xFFC0533A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🛒", fontSize = 34.sp)
                }
            }

            Spacer(Modifier.height(32.dp))

            Text(
                "Halli-Santhe",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "DIGITAL MARKETPLACE",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 3.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(Modifier.height(48.dp))

            // Dotted progress indicator
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { i ->
                    val dotPulse by infiniteTransition.animateFloat(
                        initialValue = 0.4f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            tween(600, delayMillis = i * 200),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot$i"
                    )
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .alpha(dotPulse)
                            .clip(CircleShape)
                            .background(Color(0xFF69F0AE))
                    )
                }
            }
        }

        // Floating emojis
        FloatingEmoji("🥕", 0.15f, 0.05f, 18)
        FloatingEmoji("🌿", 0.85f, 0.1f, 16)
        FloatingEmoji("🧺", 0.2f, 0.8f, 18)
        FloatingEmoji("🏺", 0.78f, 0.75f, 16)
    }
}
