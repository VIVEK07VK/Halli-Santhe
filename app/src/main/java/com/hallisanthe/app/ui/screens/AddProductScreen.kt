package com.hallisanthe.app.ui.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.ProductUploadViewModel
import com.hallisanthe.app.ui.components.AppTextField
import com.hallisanthe.app.viewmodel.UploadState

/**
 * AddProductScreen: Professional product creation with robust image upload.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val viewModel: ProductUploadViewModel = viewModel()
    val uploadState by viewModel.uploadState.collectAsState()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("kg") }
    var category by remember { mutableStateOf("Vegetables") }
    
    // Use rememberSaveable for Uri to persist during process death
    var imageUri by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf<Uri?>(null) }

    val units = listOf("kg", "pc", "jar", "packet", "litre", "gm")
    val categories = listOf(
        "Vegetables", "Fruits", "Handicrafts", "Pickles", 
        "Snacks", "Traditional Foods", "Organic Products", "Village Specials"
    )

    var unitExpanded by remember { mutableStateOf(false) }
    var catExpanded by remember { mutableStateOf(false) }

    // --- Image Picker Logic ---
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imageUri = uri
        }
    }

    // --- Permission Handling ---
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            photoPickerLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied. Cannot pick image.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uploadState) {
        when (uploadState) {
            is UploadState.Success -> {
                Toast.makeText(context, "Product published successfully!", Toast.LENGTH_LONG).show()
                onSuccess()
                viewModel.resetState()
            }
            is UploadState.Error -> {
                Toast.makeText(context, (uploadState as UploadState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Product", fontWeight = FontWeight.Bold, color = PrimaryGreenDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryGreenDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Image Selection Card ──────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clickable { 
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        } else {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Preview",
                            modifier = Modifier.fillMaxSize().padding(8.dp),
                            contentScale = ContentScale.Fit
                        )
                        Surface(
                            modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.5f)
                        ) {
                            IconButton(onClick = { imageUri = null }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                        }
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(56.dp),
                                tint = PrimaryGreen.copy(alpha = 0.3f)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Select Product Image", fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                            Text("Supports JPG, PNG, WEBP", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
            }

            // ── Form Section ──────────────────────────────────────
            Text("Product Details", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)

            AppTextField(
                value = name,
                onValueChange = { name = it },
                label = "Product Name",
                placeholder = "e.g. Village Honey Jar",
                modifier = Modifier.fillMaxWidth()
            )

            AppTextField(
                value = description,
                onValueChange = { description = it },
                label = "Description",
                placeholder = "Authentic description of your product...",
                modifier = Modifier.fillMaxWidth().height(120.dp),
                singleLine = false
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AppTextField(
                    value = price,
                    onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) price = it },
                    label = "Price (₹)",
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                AppTextField(
                    value = stock,
                    onValueChange = { if (it.all { char -> char.isDigit() }) stock = it },
                    label = "Stock",
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Unit Dropdown
                Box(modifier = Modifier.weight(1f)) {
                    ExposedDropdownMenuBox(
                        expanded = unitExpanded,
                        onExpandedChange = { unitExpanded = !unitExpanded }
                    ) {
                        AppTextField(
                            value = unit,
                            onValueChange = {},
                            readOnly = true,
                            label = "Unit",
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitExpanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(expanded = unitExpanded, onDismissRequest = { unitExpanded = false }) {
                            units.forEach { selection ->
                                DropdownMenuItem(
                                    text = { Text(selection) },
                                    onClick = {
                                        unit = selection
                                        unitExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Category Dropdown
                Box(modifier = Modifier.weight(1.5f)) {
                    ExposedDropdownMenuBox(
                        expanded = catExpanded,
                        onExpandedChange = { catExpanded = !catExpanded }
                    ) {
                        AppTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            label = "Category",
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = catExpanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(expanded = catExpanded, onDismissRequest = { catExpanded = false }) {
                            categories.forEach { selection ->
                                DropdownMenuItem(
                                    text = { Text(selection) },
                                    onClick = {
                                        category = selection
                                        catExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Upload Progress UI ────────────────────────────────
            if (uploadState is UploadState.ProcessingImage || uploadState is UploadState.SavingProduct) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen.copy(alpha = 0.05f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = PrimaryGreen)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = if (uploadState is UploadState.ProcessingImage) "Uploading image..." else "Saving product data...",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryGreenDark
                        )
                    }
                }
            }

            // ── Publish Button ────────────────────────────────────
            Button(
                onClick = {
                    viewModel.uploadProduct(name, description, price, stock, category, unit, imageUri)
                },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreenDark),
                enabled = uploadState == UploadState.Idle
            ) {
                Text("PUBLISH PRODUCT", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                enabled = uploadState == UploadState.Idle
            ) {
                Text("Discard Draft", color = DiscountRed.copy(alpha = 0.7f))
            }
            
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
