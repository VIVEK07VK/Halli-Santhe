package com.hallisanthe.app.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.SellerProfileViewModel
import com.hallisanthe.app.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProfileScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit,
    profileViewModel: SellerProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val profile by profileViewModel.sellerProfile.collectAsState()
    val productCount by profileViewModel.productCount.collectAsState()
    val orderCount by profileViewModel.orderCount.collectAsState()
    val isUpdating by profileViewModel.isUpdating.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { profileViewModel.uploadProfileImage(context, it) }
    }

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seller Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9F4EE))
            )
        },
        containerColor = Color(0xFFF9F4EE)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = Color.LightGray
                ) {
                    if (isUpdating) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color(0xFF2E7D32))
                        }
                    } else {
                        AsyncImage(
                            model = profile?.profileImageUrl ?: "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                            contentDescription = "Profile Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Surface(
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    color = Color(0xFF2E7D32),
                    onClick = { imagePickerLauncher.launch("image/*") }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(profile?.fullName ?: "Seller Name", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B3A2D))
            Text(profile?.shopName ?: "Shop Name", fontSize = 14.sp, color = Color.Gray)

            Spacer(Modifier.height(24.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("Products", productCount.toString())
                StatItem("Orders", orderCount.toString())
                StatItem("Rating", "4.8 ⭐")
            }

            Spacer(Modifier.height(24.dp))

            // Action Cards
            ProfileMenuSection(
                title = "Order Management",
                items = listOf(
                    ProfileMenuItem("Manage Orders", Icons.Default.ShoppingBag, { onNavigate(Routes.SELLER_ORDERS) }),
                    ProfileMenuItem("Earnings History", Icons.Default.Payments, { onNavigate(Routes.SELLER_EARNINGS) })
                )
            )

            Spacer(Modifier.height(16.dp))

            ProfileMenuSection(
                title = "Shop Settings",
                items = listOf(
                    ProfileMenuItem("Edit Profile", Icons.Default.Edit, { onNavigate(Routes.EDIT_PROFILE) }),
                    ProfileMenuItem("Business Address", Icons.Default.LocationOn, { onNavigate(Routes.SAVED_ADDRESSES) }),
                    ProfileMenuItem("Manage Products", Icons.Default.Inventory, { onNavigate(Routes.SELLER_PRODUCTS) })
                )
            )

            Spacer(Modifier.height(16.dp))

            ProfileMenuSection(
                title = "Account",
                items = listOf(
                    ProfileMenuItem("Help & Support", Icons.Default.SupportAgent, { onNavigate(Routes.HELP_SUPPORT) }),
                    ProfileMenuItem("Logout", Icons.Default.Logout, {
                        authViewModel.logout()
                        onLogout()
                    }, color = Color.Red)
                )
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Card(
        modifier = Modifier.width(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF2E7D32))
            Text(label, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

data class ProfileMenuItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val color: Color = Color(0xFF1B3A2D)
)

@Composable
fun ProfileMenuSection(title: String, items: List<ProfileMenuItem>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { item.onClick() }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(item.icon, contentDescription = null, tint = item.color, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(16.dp))
                        Text(item.title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = item.color, modifier = Modifier.weight(1f))
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                    }
                    if (index < items.size - 1) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF5F5F5))
                    }
                }
            }
        }
    }
}
