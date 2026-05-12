package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProfileScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    onEditProfile: () -> Unit,
    onMyOrders: () -> Unit,
    onSavedAddresses: () -> Unit,
    onRecentlyViewed: () -> Unit,
    onHelpSupport: () -> Unit,
    onAbout: () -> Unit,
    onLogout: () -> Unit
) {
    val userProfile by profileViewModel.userProfile.collectAsState()
    
    val displayName = userProfile?.fullName?.takeIf { it.isNotBlank() } ?: "Seller"
    val initials = displayName.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString("")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Seller Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(80.dp).clip(CircleShape).background(PrimaryGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = userProfile?.profileImageUrl ?: "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                        Text(userProfile?.shopName ?: "Artisan Shop", fontSize = 14.sp, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            color = PrimaryGreen.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("SELLER", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                    IconButton(onClick = onEditProfile) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = PrimaryGreen)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Menu Options
            ProfileMenuItem(icon = Icons.Default.ShoppingBag, title = "My Orders", onClick = onMyOrders)
            ProfileMenuItem(icon = Icons.Default.LocationOn, title = "Saved Addresses", onClick = onSavedAddresses)
            ProfileMenuItem(icon = Icons.Default.History, title = "Recently Viewed", onClick = onRecentlyViewed)
            ProfileMenuItem(icon = Icons.Default.Help, title = "Help & Support", onClick = onHelpSupport)
            ProfileMenuItem(icon = Icons.Default.Info, title = "About Halli-Santhe", onClick = onAbout)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontWeight = FontWeight.Bold)
            }
        }
    }
}
