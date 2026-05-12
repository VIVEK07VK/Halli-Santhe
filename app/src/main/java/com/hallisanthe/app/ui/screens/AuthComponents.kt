package com.hallisanthe.app.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.AuthUiState
import kotlinx.coroutines.delay

// ─── App color palette ────────────────────────────────────────────────────────

private val GreenPrimary    = Color(0xFF2E7D32)
private val GreenLight      = Color(0xFF4CAF50)
private val GreenAccent     = Color(0xFF00C853)
private val OrangePrimary   = Color(0xFFE65100)
private val OrangeAccent    = Color(0xFFFF6D00)
private val YellowAccent    = Color(0xFFFFD600)
private val CreamBg         = Color(0xFFF9F4EE)
private val DarkTeal        = Color(0xFF1B3A2D)
private val CardWhite       = Color(0xFFFFFFFF)
private val CardDarkBg      = Color(0xFF1E3A3A)
private val TerraCotta      = Color(0xFFC0533A)
private val TextOnDark      = Color(0xFFFFFFFF)
private val TextSecondary   = Color(0xFF6B7280)
private val ErrorRed        = Color(0xFFD32F2F)

// ─── Shared reusable composables ─────────────────────────────────────────────

@Composable
fun AuthGradientBackground(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B3A2D),
                        Color(0xFF2E7D32),
                        Color(0xFF1B5E20)
                    )
                )
            ),
        content = content
    )
}

@Composable
fun HalliInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggle: () -> Unit = {},
    error: String? = null,
    keyboardType: androidx.compose.ui.text.input.KeyboardType =
        androidx.compose.ui.text.input.KeyboardType.Text
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, fontSize = 13.sp) },
            leadingIcon = {
                Icon(leadingIcon, contentDescription = label, tint = GreenLight)
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = onPasswordToggle) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff
                                          else Icons.Filled.Visibility,
                            contentDescription = "Toggle password",
                            tint = TextSecondary
                        )
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !passwordVisible)
                androidx.compose.ui.text.input.PasswordVisualTransformation()
            else
                androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = keyboardType
            ),
            isError = error != null,
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenAccent,
                unfocusedBorderColor = Color(0xFFCFD8DC),
                focusedLabelColor = GreenAccent,
                cursorColor = GreenAccent,
                errorBorderColor = ErrorRed,
                errorLabelColor = ErrorRed,
                focusedContainerColor = CardWhite,
                unfocusedContainerColor = CardWhite,
                errorContainerColor = Color(0xFFFFF8F8)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = error != null) {
            Text(
                text = error ?: "",
                color = ErrorRed,
                fontSize = 11.sp,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}

@Composable
fun PrimaryAuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary,
            contentColor = Color.White,
            disabledContainerColor = GreenPrimary.copy(alpha = 0.5f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.5.dp
            )
        } else {
            Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
        }
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    OutlinedButton(
        onClick = onClick,
        enabled = !isLoading,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = CardWhite),
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = GreenPrimary,
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.5.dp
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Google "G" icon
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(Color(0xFF4285F4), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("G", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    "Continue with Google",
                    color = Color(0xFF3C4043),
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun AuthCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        content = { Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp), content = content) }
    )
}

@Composable
fun FloatingEmoji(emoji: String, xFraction: Float, yFraction: Float, size: Int = 20) {
    val infiniteTransition = rememberInfiniteTransition(label = "float_$emoji")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000 + (xFraction * 1000).toInt(), easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_y"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = offsetY.dp)
    ) {
        Text(
            text = emoji,
            fontSize = size.sp,
            modifier = Modifier
                .fillMaxWidth(xFraction)
                .padding(top = (yFraction * 200).dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}
