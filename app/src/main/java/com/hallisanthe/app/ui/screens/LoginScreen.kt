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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hallisanthe.app.R
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.viewmodel.AuthUiState
import com.hallisanthe.app.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    role: UserRole,
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: (UserRole) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState by authViewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }


    // State observation
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthUiState.Success -> {
                val userRole = state.user?.role?.let { roleName ->
                    UserRole.values().firstOrNull { it.name == roleName } ?: role
                } ?: role
                onLoginSuccess(userRole)
                authViewModel.resetState()
            }
            else -> Unit
        }
    }

    val isLoading = uiState is AuthUiState.Loading

    // Inline validation
    fun validateAndLogin() {
        emailError = null
        passwordError = null
        var valid = true
        if (email.isBlank()) { emailError = "Email is required"; valid = false }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            emailError = "Invalid email address"; valid = false
        }
        if (password.isBlank()) { passwordError = "Password is required"; valid = false }
        if (valid) authViewModel.loginWithEmail(email.trim(), password, role)
    }

    // Entrance animation
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600),
        label = "loginAlpha"
    )
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
        // Decorative emojis
        FloatingEmoji(if (role == UserRole.BUYER) "🛍️" else "🏪", 0.08f, 0.04f, 20)
        FloatingEmoji("🌿", 0.85f, 0.08f, 18)
        FloatingEmoji("🥬", 0.12f, 0.72f, 18)
        FloatingEmoji("🌾", 0.82f, 0.68f, 18)

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
                    .padding(top = 52.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Back button
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                        .size(40.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }

                Spacer(Modifier.height(20.dp))

                // Role chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (role == UserRole.BUYER) Color(0xFF69F0AE).copy(alpha = 0.2f)
                            else Color(0xFFFFAB40).copy(alpha = 0.2f)
                        )
                        .padding(horizontal = 14.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = if (role == UserRole.BUYER) "🛍️  Buyer Login" else "🏪  Seller Login",
                        color = if (role == UserRole.BUYER) Color(0xFF69F0AE) else Color(0xFFFFAB40),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(10.dp))
                Text(
                    "Welcome back!",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    "Sign in to continue to your ${if (role == UserRole.BUYER) "marketplace" else "seller dashboard"}",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp
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
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 28.dp)
                ) {
                    // Global error from Firebase
                    if (uiState is AuthUiState.Error) {
                        val errMsg = (uiState as AuthUiState.Error).message
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Filled.ErrorOutline, contentDescription = null, tint = Color(0xFFD32F2F), modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(errMsg, color = Color(0xFFD32F2F), fontSize = 13.sp)
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                    }

                    HalliInputField(
                        value = email,
                        onValueChange = { email = it; emailError = null; authViewModel.resetState() },
                        label = "Email Address",
                        leadingIcon = Icons.Filled.Email,
                        error = emailError,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Email
                    )
                    Spacer(Modifier.height(12.dp))

                    HalliInputField(
                        value = password,
                        onValueChange = { password = it; passwordError = null; authViewModel.resetState() },
                        label = "Password",
                        leadingIcon = Icons.Filled.Lock,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordToggle = { passwordVisible = !passwordVisible },
                        error = passwordError
                    )

                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        onClick = onNavigateToForgotPassword,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Forgot Password?", color = Color(0xFF2E7D32), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(8.dp))
                    PrimaryAuthButton(
                        text = "Sign In",
                        onClick = { validateAndLogin() },
                        isLoading = isLoading
                    )


                    Spacer(Modifier.height(28.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Don't have an account?", color = Color(0xFF6B7280), fontSize = 14.sp)
                        TextButton(onClick = onNavigateToRegister) {
                            Text("Sign Up", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
