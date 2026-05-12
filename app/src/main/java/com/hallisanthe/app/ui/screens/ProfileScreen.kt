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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.models.CartItem
import com.hallisanthe.app.models.OrderStatus
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.ProfileViewModel
import com.hallisanthe.app.ui.components.HomeBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    cartViewModel: CartViewModel,
    profileViewModel: ProfileViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToAddresses: () -> Unit,
    onNavigateToRecentlyViewed: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val sessionUser by authViewModel.sessionUser.collectAsState()
    val userProfile by profileViewModel.userProfile.collectAsState()
    val cartItems   by cartViewModel.cartItems.collectAsState()
    val cartCount   = cartItems.sumOf { it.quantity }

    val displayName  = userProfile?.fullName?.takeIf { it.isNotBlank() } ?: sessionUser?.fullName?.takeIf { it.isNotBlank() } ?: "User"
    val displayEmail = userProfile?.email?.takeIf { it.isNotBlank() } ?: sessionUser?.email?.takeIf { it.isNotBlank() } ?: "—"
    val initials     = displayName.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString("")

    Scaffold(
        topBar = {
            TopAppBar(
                title          = { Text("Profile", fontWeight = FontWeight.Bold, color = PrimaryGreenDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryGreenDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        bottomBar = {
            HomeBottomNavigation(
                currentRoute          = "Profile",
                cartCount             = cartCount,
                onNavigateToHome      = onNavigateToHome,
                onNavigateToSearch    = onNavigateToSearch,
                onNavigateToFavorites = onNavigateToFavorites,
                onNavigateToCart      = onNavigateToCart,
                onNavigateToProfile   = onNavigateToProfile
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
            // User Info Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(containerColor = SurfaceLight)
            ) {
                Row(
                    modifier          = Modifier.padding(20.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier         = Modifier.size(72.dp).clip(CircleShape).background(PrimaryGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        if (initials.isNotEmpty()) {
                            Text(
                                text       = initials,
                                fontSize   = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color      = SurfaceLight
                            )
                        } else {
                            Icon(Icons.Default.Person, contentDescription = null, tint = SurfaceLight, modifier = Modifier.size(36.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                        Text(displayEmail, fontSize = 14.sp, color = TextSecondary)
                        if (sessionUser?.phone?.isNotBlank() == true) {
                            Text(sessionUser!!.phone, fontSize = 13.sp, color = TextSecondary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryGreen.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("BUYER", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Menu items
            ProfileMenuItem(icon = Icons.Default.ShoppingBag,   title = "My Orders",        onClick = onNavigateToOrders)
            ProfileMenuItem(icon = Icons.Default.LocationOn,     title = "Saved Addresses",   onClick = onNavigateToAddresses)
            ProfileMenuItem(icon = Icons.Default.Favorite,       title = "My Wishlist",       onClick = onNavigateToFavorites)
            ProfileMenuItem(icon = Icons.Default.History,        title = "Recently Viewed",   onClick = onNavigateToRecentlyViewed)
            ProfileMenuItem(icon = Icons.Default.Help,           title = "Help & Support",    onClick = onNavigateToHelp)
            ProfileMenuItem(icon = Icons.Default.Info,           title = "About Halli-Santhe", onClick = onNavigateToAbout)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick  = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(containerColor = DiscountRed),
                shape    = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).clickable(onClick = onClick),
        colors   = CardDefaults.cardColors(containerColor = SurfaceLight),
        shape    = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier          = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryGreenDark, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = TextSecondary)
        }
    }
}
