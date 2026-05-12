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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.viewmodel.AuthUiState
import com.hallisanthe.app.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    role: UserRole,
    authViewModel: AuthViewModel = viewModel(),
    onRegisterSuccess: (UserRole) -> Unit,
    onNavigateToLogin: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by authViewModel.uiState.collectAsState()

    // Form state
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }
    var shopName by remember { mutableStateOf("") }
    var villageName by remember { mutableStateOf("") }

    // Selected role (user can switch)
    var selectedRole by remember { mutableStateOf(role) }

    // Derived state
    val isLoading = uiState is AuthUiState.Loading

    // Navigate on success — only fires once per real Success event
    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            val state = uiState as AuthUiState.Success
            val userRole = state.user?.role?.let { r ->
                UserRole.values().firstOrNull { it.name == r } ?: selectedRole
            } ?: selectedRole
            onRegisterSuccess(userRole)
            authViewModel.resetState()
        }
    }

    // Entrance animation
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (visible) 1f else 0f, tween(600), label = "regAlpha")
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
        // Floating emojis
        FloatingEmoji("🌽", 0.08f, 0.02f, 20)
        FloatingEmoji("🫙", 0.88f, 0.06f, 18)
        FloatingEmoji("🍅", 0.1f, 0.75f, 18)
        FloatingEmoji("🧵", 0.84f, 0.72f, 18)

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
                    onClick = {
                        authViewModel.resetState()
                        onBack()
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                        .size(40.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (selectedRole == UserRole.BUYER) Color(0xFF69F0AE).copy(0.2f)
                            else Color(0xFFFFAB40).copy(0.2f)
                        )
                        .padding(horizontal = 14.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = if (selectedRole == UserRole.BUYER) "🛍️  Buyer Registration" else "🏪  Seller Registration",
                        color = if (selectedRole == UserRole.BUYER) Color(0xFF69F0AE) else Color(0xFFFFAB40),
                        fontSize = 12.sp, fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text("Create Account", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
                Text("Join the village marketplace community", color = Color.White.copy(0.7f), fontSize = 13.sp)
            }

            // Form card
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
                    // ── Error Banner ──
                    // Show specific error messages — never the generic fallback
                    AnimatedVisibility(visible = uiState is AuthUiState.Error) {
                        val errMsg = (uiState as? AuthUiState.Error)?.message ?: ""
                        if (errMsg.isNotBlank()) {
                            Column {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Filled.ErrorOutline,
                                            contentDescription = null,
                                            tint = Color(0xFFD32F2F),
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            text = errMsg,
                                            color = Color(0xFFD32F2F),
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                            }
                        }
                    }

                    // ── Role Toggle ──
                    Text("Your Role", fontSize = 13.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE8F5E9))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        UserRole.values().forEach { r ->
                            val isSelected = selectedRole == r
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) Color(0xFF2E7D32) else Color.Transparent)
                                    .clickable(enabled = !isLoading) {
                                        selectedRole = r
                                        authViewModel.resetState()
                                    }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (r == UserRole.BUYER) "🛍️ Buyer" else "🏪 Seller",
                                    color = if (isSelected) Color.White else Color(0xFF388E3C),
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // ── Personal fields ──
                    HalliInputField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "Full Name",
                        leadingIcon = Icons.Filled.Person
                    )
                    Spacer(Modifier.height(12.dp))

                    HalliInputField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email Address",
                        leadingIcon = Icons.Filled.Email,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Email
                    )
                    Spacer(Modifier.height(12.dp))

                    HalliInputField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Phone Number",
                        leadingIcon = Icons.Filled.Phone,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                    )
                    Spacer(Modifier.height(12.dp))

                    HalliInputField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        leadingIcon = Icons.Filled.Lock,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordToggle = { passwordVisible = !passwordVisible }
                    )
                    Spacer(Modifier.height(12.dp))

                    HalliInputField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Confirm Password",
                        leadingIcon = Icons.Filled.LockOpen,
                        isPassword = true,
                        passwordVisible = confirmVisible,
                        onPasswordToggle = { confirmVisible = !confirmVisible }
                    )

                    // ── Seller-only fields ──
                    AnimatedVisibility(visible = selectedRole == UserRole.SELLER) {
                        Column {
                            Spacer(Modifier.height(20.dp))
                            Divider(color = Color(0xFFDDDDDD))
                            Spacer(Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE65100).copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🏪", fontSize = 16.sp)
                                }
                                Spacer(Modifier.width(8.dp))
                                Text("Shop Details", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B3A2D))
                            }
                            Spacer(Modifier.height(12.dp))
                            HalliInputField(
                                value = shopName,
                                onValueChange = { shopName = it },
                                label = "Shop / Store Name",
                                leadingIcon = Icons.Filled.Store
                            )
                            Spacer(Modifier.height(12.dp))
                            HalliInputField(
                                value = villageName,
                                onValueChange = { villageName = it },
                                label = "Village / Town Name",
                                leadingIcon = Icons.Filled.LocationOn
                            )
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    // ── Create Account Button ──
                    PrimaryAuthButton(
                        text = if (isLoading) "Creating Account…" else "Create Account",
                        onClick = {
                            authViewModel.register(
                                fullName        = fullName,
                                email           = email,
                                phone           = phone,
                                password        = password,
                                confirmPassword = confirmPassword,
                                role            = selectedRole,
                                shopName        = shopName,
                                villageName     = villageName
                            )
                        },
                        isLoading = isLoading
                    )

                    Spacer(Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Already have an account?", color = Color(0xFF6B7280), fontSize = 14.sp)
                        TextButton(
                            onClick = {
                                authViewModel.resetState()
                                onNavigateToLogin()
                            },
                            enabled = !isLoading
                        ) {
                            Text("Sign In", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
