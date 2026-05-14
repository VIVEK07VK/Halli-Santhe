package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hallisanthe.app.ui.components.AppTextField
import com.hallisanthe.app.viewmodel.SellerProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSellerProfileScreen(
    onBack: () -> Unit,
    profileViewModel: SellerProfileViewModel = viewModel()
) {
    val profile by profileViewModel.sellerProfile.collectAsState()
    val isUpdating by profileViewModel.isUpdating.collectAsState()
    val updateSuccess by profileViewModel.updateSuccess.collectAsState()

    var fullName by remember { mutableStateOf(profile?.fullName ?: "") }
    var shopName by remember { mutableStateOf(profile?.shopName ?: "") }
    var phone by remember { mutableStateOf(profile?.phone ?: "") }
    var businessAddress by remember { mutableStateOf(profile?.businessAddress ?: "") }
    var profileImageUrl by remember { mutableStateOf(profile?.profileImageUrl ?: "") }

    LaunchedEffect(profile) {
        profile?.let {
            fullName = it.fullName
            shopName = it.shopName
            phone = it.phone
            businessAddress = it.businessAddress
            profileImageUrl = it.profileImageUrl
        }
    }

    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            profileViewModel.resetUpdateState()
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            profileViewModel.updateProfile(fullName, shopName, phone, businessAddress, profileImageUrl)
                        },
                        enabled = !isUpdating
                    ) {
                        if (isUpdating) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            Text("SAVE", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        }
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    color = Color.LightGray
                ) {
                    AsyncImage(
                        model = profileImageUrl.ifEmpty { "https://cdn-icons-png.flaticon.com/512/3135/3135715.png" },
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Surface(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    color = Color(0xFF2E7D32),
                    onClick = { /* In real app, open image picker */ }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Change", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            AppTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = shopName,
                onValueChange = { shopName = it },
                label = "Shop / Business Name",
                leadingIcon = Icons.Default.Storefront,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Phone Number",
                leadingIcon = Icons.Default.Phone,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                )
            )

            Spacer(Modifier.height(16.dp))

            AppTextField(
                value = businessAddress,
                onValueChange = { businessAddress = it },
                label = "Business Address",
                leadingIcon = Icons.Default.LocationOn,
                modifier = Modifier.fillMaxWidth(),
                singleLine = false
            )

            Spacer(Modifier.height(40.dp))
            
            Text(
                "Your information is stored securely and only used for marketplace operations.",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
