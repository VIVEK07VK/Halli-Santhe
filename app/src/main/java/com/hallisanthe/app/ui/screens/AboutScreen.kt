package com.hallisanthe.app.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo Placeholder
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = PrimaryGreen.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("HS", fontSize = 40.sp, fontWeight = FontWeight.Black, color = PrimaryGreen)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Halli-Santhe", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
            Text("Version 1.0.0", fontSize = 14.sp, color = TextSecondary)

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Mission",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrimaryGreenDark,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "“Halli-Santhe connects village artisans and local sellers directly with nearby customers through a modern hyperlocal marketplace.”",
                fontSize = 15.sp,
                color = TextSecondary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Description",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = PrimaryGreenDark,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "We empower rural communities by providing them a digital platform to showcase their unique products, from organic vegetables to handmade crafts. Our goal is to preserve traditional methods while embracing modern convenience.",
                fontSize = 15.sp,
                color = TextSecondary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(40.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                SocialIcon(Icons.Default.Language, "Website") { openUrl(context, "https://hallisanthe.com") }
                SocialIcon(Icons.Default.Language, "Instagram") { openUrl(context, "https://instagram.com/hallisanthe") }
                SocialIcon(Icons.Default.Language, "Facebook") { openUrl(context, "https://facebook.com/hallisanthe") }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            Text("© 2026 Halli-Santhe Digital. All rights reserved.", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SocialIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(icon, contentDescription = label, tint = PrimaryGreenDark, modifier = Modifier.size(32.dp))
    }
}

private fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
