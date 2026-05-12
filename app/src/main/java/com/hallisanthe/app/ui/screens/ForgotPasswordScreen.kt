package com.hallisanthe.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hallisanthe.app.viewmodel.AuthUiState
import com.hallisanthe.app.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel = viewModel(),
    onBack: () -> Unit,
    onResetSent: () -> Unit
) {
    val uiState by authViewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }

    // Navigate on success
    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.PasswordResetSent) {
            delay(200)
            onResetSent()
            authViewModel.resetState()
        }
    }

    val isLoading = uiState is AuthUiState.Loading

    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (visible) 1f else 0f, tween(600), label = "fpAlpha")
    LaunchedEffect(Unit) { delay(100); visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1B3A2D), Color(0xFF2E7D32), Color(0xFF388E3C))
                )
            )
    ) {
        FloatingEmoji("🔑", 0.1f, 0.03f, 22)
        FloatingEmoji("📧", 0.85f, 0.05f, 20)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 52.dp, bottom = 20.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(0.15f))
                        .size(40.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }

                Spacer(Modifier.height(32.dp))

                // Lock icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🔐", fontSize = 36.sp)
                }

                Spacer(Modifier.height(20.dp))
                Text("Forgot Password?", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                Text(
                    "No worries! Enter your email and we'll send a reset link",
                    color = Color.White.copy(0.7f), fontSize = 13.sp, lineHeight = 18.sp
                )
            }

            // Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    // Error state
                    if (uiState is AuthUiState.Error) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.ErrorOutline, contentDescription = null, tint = Color(0xFFD32F2F), modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text((uiState as AuthUiState.Error).message, color = Color(0xFFD32F2F), fontSize = 13.sp)
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                    }

                    Text("Email Address", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1B3A2D))
                    Spacer(Modifier.height(8.dp))

                    HalliInputField(
                        value = email,
                        onValueChange = { email = it; emailError = null; authViewModel.resetState() },
                        label = "your@email.com",
                        leadingIcon = Icons.Filled.Email,
                        error = emailError,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Email
                    )

                    Spacer(Modifier.height(28.dp))

                    PrimaryAuthButton(
                        text = "Send Reset Link",
                        onClick = {
                            if (email.isBlank()) {
                                emailError = "Email is required"
                            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
                                emailError = "Invalid email address"
                            } else {
                                authViewModel.sendPasswordReset(email.trim())
                            }
                        },
                        isLoading = isLoading
                    )

                    Spacer(Modifier.height(24.dp))

                    // Info box
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                            Text("ℹ️", fontSize = 18.sp)
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text("How it works", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "We'll send a password reset link to your registered email. Check your inbox (and spam folder) and follow the link to set a new password.",
                                    fontSize = 12.sp, color = Color(0xFF388E3C), lineHeight = 17.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Remember your password?", color = Color(0xFF6B7280), fontSize = 14.sp)
                        TextButton(onClick = onBack) {
                            Text("Sign In", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

// ─── Password Reset Sent Confirmation ─────────────────────────────────────────

@Composable
fun ResetEmailSentScreen(onBackToLogin: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (visible) 1f else 0f, tween(600), label = "sentAlpha")
    val scale by animateFloatAsState(if (visible) 1f else 0.8f, tween(700, easing = FastOutSlowInEasing), label = "sentScale")
    LaunchedEffect(Unit) { delay(100); visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1B3A2D), Color(0xFF2E7D32)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha)
                    .clip(CircleShape)
                    .background(Color.White.copy(0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text("✉️", fontSize = 56.sp)
            }

            Spacer(Modifier.height(28.dp))
            Text("Check Your Email!", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif, textAlign = TextAlign.Center)
            Spacer(Modifier.height(12.dp))
            Text(
                "We've sent a password reset link. Please check your inbox and spam folder.",
                color = Color.White.copy(0.75f), fontSize = 14.sp, textAlign = TextAlign.Center, lineHeight = 20.sp
            )

            Spacer(Modifier.height(40.dp))
            Button(
                onClick = onBackToLogin,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF2E7D32)),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Back to Sign In", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}
