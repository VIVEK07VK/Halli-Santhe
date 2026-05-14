package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.Address
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.ProfileViewModel
import com.hallisanthe.app.ui.components.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedAddressesScreen(
    profileViewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val addresses by profileViewModel.addresses.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Addresses", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = PrimaryGreen,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Address")
            }
        }
    ) { paddingValues ->
        if (addresses.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No addresses saved", color = TextSecondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(addresses) { address ->
                    AddressCard(address, onDelete = { profileViewModel.deleteAddress(address.id) })
                }
            }
        }

        if (showAddDialog) {
            AddAddressDialog(
                onDismiss = { showAddDialog = false },
                onSave = { title, village, landmark, pincode, phone ->
                    profileViewModel.addAddress(title, village, landmark, pincode, phone)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun AddressCard(address: Address, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryGreen)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(address.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${address.villageTown}, ${address.landmark}", fontSize = 14.sp, color = TextSecondary)
                Text("Pincode: ${address.pincode}", fontSize = 14.sp, color = TextSecondary)
                Text("Phone: ${address.phone}", fontSize = 14.sp, color = TextSecondary)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Address") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AppTextField(value = title, onValueChange = { title = it }, label = "Address Title (e.g. Home, Shop)")
                AppTextField(value = village, onValueChange = { village = it }, label = "Village/Town")
                AppTextField(value = landmark, onValueChange = { landmark = it }, label = "Landmark")
                AppTextField(value = pincode, onValueChange = { pincode = it }, label = "Pincode")
                AppTextField(value = phone, onValueChange = { phone = it }, label = "Phone Number")
            }
        },
        confirmButton = {
            Button(onClick = { onSave(title, village, landmark, pincode, phone) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
