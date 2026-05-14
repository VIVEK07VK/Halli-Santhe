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

@Composable
fun LoadingScreen(
    authViewModel: AuthViewModel,
    onNavigateToBuyerHome: () -> Unit,
    onNavigateToSellerDashboard: () -> Unit,
    onNavigateToRoleSelection: () -> Unit
) {
    val uiState by authViewModel.uiState.collectAsState()
    var hasNavigated by remember { mutableStateOf(false) }

    // Initial session check
    LaunchedEffect(Unit) {
        authViewModel.checkSession()
    }

    // Single-entry navigation logic
    LaunchedEffect(uiState) {
        if (hasNavigated) return@LaunchedEffect

        when (val state = uiState) {
            is AuthUiState.Success -> {
                hasNavigated = true
                delay(800) // Allow animation to settle
                if (state.user.role == UserRole.SELLER.name) {
                    onNavigateToSellerDashboard()
                } else {
                    onNavigateToBuyerHome()
                }
            }
            is AuthUiState.Unauthenticated -> {
                hasNavigated = true
                delay(800)
                onNavigateToRoleSelection()
            }
            is AuthUiState.Error -> {
                // On error, fallback to role selection/login to avoid "stuck" state
                hasNavigated = true
                delay(800)
                onNavigateToRoleSelection()
            }
            else -> Unit
        }
    }

    // --- UI/Animations (remains unchanged for rich aesthetic) ---
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
            Spacer(Modifier.height(48.dp))
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
    }
}
