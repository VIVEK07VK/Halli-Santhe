package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.EarningsViewModel
import com.hallisanthe.app.viewmodel.SellerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(
    sellerViewModel: SellerViewModel,
    earningsViewModel: EarningsViewModel = viewModel(),
    onNavigateToOrders: () -> Unit,
    onNavigateToAddProduct: () -> Unit,
    onLogout: () -> Unit
) {
    val sellerProducts  by sellerViewModel.sellerProducts.collectAsState()
    val sellerInquiries by sellerViewModel.sellerInquiries.collectAsState()
    val orders          by sellerViewModel.sellerOrders.collectAsState()
    val earningsSummary by earningsViewModel.earningsSummary.collectAsState()

    val pendingOrdersCount = orders.count {
        it.orderStatus in listOf("PENDING", "WAITING_CONFIRMATION", "ACCEPTED", "PREPARING", "PACKED")
    }

    // Edit Product Dialog state
    var editProduct    by remember { mutableStateOf<Product?>(null) }

    if (editProduct != null) {
        // Keeping edit dialog for now as user only asked to replace "Add Product"
        // But in a real app, Edit should also probably be a full screen.
        // For now, I'll keep the legacy dialog for Edit to stay within scope of request.
        AddEditProductDialog(
            existingProduct = editProduct,
            onDismiss       = { editProduct = null },
            onSave          = { name, price, stock, category, imageUrl, unit ->
                sellerViewModel.updateProduct(editProduct!!.copy(name = name, price = price, stock = stock, category = category, imageUrl = imageUrl, unit = unit))
                editProduct = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Seller Dashboard", fontWeight = FontWeight.Bold, color = PrimaryGreenDark, fontSize = 20.sp)
                        Text("Halli-Santhe Marketplace", color = TextSecondary, fontSize = 12.sp)
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Outlined.PersonOutline, contentDescription = "Logout", tint = PrimaryGreenDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick          = onNavigateToAddProduct,
                containerColor   = SecondaryOrange,
                contentColor     = SurfaceLight,
                icon             = { Icon(Icons.Default.Add, contentDescription = "Add Product") },
                text             = { Text("Add Product", fontWeight = FontWeight.Bold) }
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        LazyColumn(
            modifier            = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding      = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Stats Row 1 ──────────────────────────────────────────────────────
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(modifier = Modifier.weight(1f), title = "Total Revenue",      value = "₹${earningsSummary.totalRevenue.toInt()}",   icon = Icons.Default.MonetizationOn, gradient = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32)))
                    StatCard(modifier = Modifier.weight(1f), title = "Today's Earnings",   value = "₹${earningsSummary.todayEarnings.toInt()}",   icon = Icons.Default.TrendingUp,     gradient = listOf(Color(0xFFFF9800), Color(0xFFE65100)))
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(modifier = Modifier.weight(1f), title = "Pending Orders",     value = pendingOrdersCount.toString(),                 icon = Icons.Default.Pending,        gradient = listOf(Color(0xFF2196F3), Color(0xFF1565C0)))
                    StatCard(modifier = Modifier.weight(1f), title = "Completed Orders",   value = earningsSummary.completedOrdersCount.toString(), icon = Icons.Default.CheckCircle,    gradient = listOf(Color(0xFF9C27B0), Color(0xFF6A1B9A)))
                }
            }

            // ── Earnings Breakdown ────────────────────────────────────────────────
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = SurfaceLight), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Earnings Breakdown", fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Sales Volume", color = TextSecondary)
                            Text("₹${earningsSummary.totalSales.toInt()}", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Platform Commission (5%)", color = DiscountRed)
                            Text("-₹${earningsSummary.totalCommissionDeducted.toInt()}", fontWeight = FontWeight.Bold, color = DiscountRed)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Net Earnings", color = PrimaryGreenDark, fontWeight = FontWeight.Bold)
                            Text("₹${earningsSummary.totalRevenue.toInt()}", fontWeight = FontWeight.ExtraBold, color = PrimaryGreenDark)
                        }
                    }
                }
            }

            // ── Manage Orders Button ──────────────────────────────────────────────
            item {
                Button(
                    onClick = onNavigateToOrders,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape    = RoundedCornerShape(12.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = BannerDark)
                ) {
                    Icon(Icons.Default.ListAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Manage Orders", fontWeight = FontWeight.Bold)
                }
            }

            // ── Inquiries ─────────────────────────────────────────────────────────
            if (sellerInquiries.isNotEmpty()) {
                item {
                    Text("Pending Inquiries", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
                }
                items(sellerInquiries, key = { it.inquiryId }) { inquiry ->
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SecondaryOrange.copy(alpha = 0.1f))) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Product: ${inquiry.productName}", fontWeight = FontWeight.Bold)
                            Text("Status: ${inquiry.responseStatus}", fontSize = 12.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { sellerViewModel.respondToInquiry(inquiry.inquiryId, "AVAILABLE") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)) { Text("Available") }
                                Button(onClick = { sellerViewModel.respondToInquiry(inquiry.inquiryId, "OUT_OF_STOCK") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = DiscountRed)) { Text("Out of Stock") }
                            }
                        }
                    }
                }
            }

            // ── Products List ─────────────────────────────────────────────────────
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Your Products (${sellerProducts.size})", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
                }
            }

            if (sellerProducts.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = SurfaceLight), shape = RoundedCornerShape(12.dp)) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("📦", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("No products yet", fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                            Text("Tap + to add your first product", color = TextSecondary, fontSize = 14.sp)
                        }
                    }
                }
            } else {
                items(sellerProducts, key = { it.id }) { product ->
                    SellerProductCard(
                        product  = product,
                        onEdit   = { editProduct = product },
                        onDelete = { sellerViewModel.deleteProduct(product.id) },
                        onUpdateStock = { newStock -> sellerViewModel.updateProductStock(product, newStock) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// ─── Add / Edit Product Dialog ────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductDialog(
    existingProduct: Product?,
    onDismiss: () -> Unit,
    onSave: (name: String, price: Double, stock: Int, category: String, imageUrl: String, unit: String) -> Unit
) {
    var name      by remember { mutableStateOf(existingProduct?.name ?: "") }
    var price     by remember { mutableStateOf(existingProduct?.price?.toString() ?: "") }
    var stock     by remember { mutableStateOf(existingProduct?.stock?.toString() ?: "") }
    var category  by remember { mutableStateOf(existingProduct?.category ?: "Vegetables") }
    var imageUrl  by remember { mutableStateOf(existingProduct?.imageUrl ?: "") }
    var unit      by remember { mutableStateOf(existingProduct?.unit ?: "kg") }

    val categories = listOf("Vegetables", "Fruits", "Groceries", "Organic", "Pickles", "Snacks", "Handicrafts")
    var catExpanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = SurfaceLight)) {
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth()) {
                Text(
                    if (existingProduct == null) "Add New Product" else "Edit Product",
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = PrimaryGreenDark
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(value = name,     onValueChange = { name = it },     label = { Text("Product Name") },    modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = price,    onValueChange = { price = it },    label = { Text("Price (₹)") },       modifier = Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = stock,    onValueChange = { stock = it },    label = { Text("Stock Quantity") },  modifier = Modifier.fillMaxWidth(), singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = unit,     onValueChange = { unit = it },     label = { Text("Unit (kg/pc/jar)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Image URL (optional)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(8.dp))

                // Category dropdown
                ExposedDropdownMenuBox(expanded = catExpanded, onExpandedChange = { catExpanded = !catExpanded }) {
                    OutlinedTextField(
                        value           = category,
                        onValueChange   = {},
                        readOnly        = true,
                        label           = { Text("Category") },
                        trailingIcon    = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = catExpanded) },
                        modifier        = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = catExpanded, onDismissRequest = { catExpanded = false }) {
                        categories.forEach { cat ->
                            DropdownMenuItem(text = { Text(cat) }, onClick = { category = cat; catExpanded = false })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            val p = price.toDoubleOrNull() ?: 0.0
                            val s = stock.toIntOrNull() ?: 0
                            if (name.isNotBlank() && p > 0) {
                                onSave(name.trim(), p, s, category, imageUrl.trim(), unit.trim().ifBlank { "kg" })
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors   = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text(if (existingProduct == null) "Add" else "Save", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ─── Stat Card ────────────────────────────────────────────────────────────────

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    gradient: List<Color>
) {
    Card(modifier = modifier.height(120.dp), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(4.dp)) {
        Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(gradient)).padding(16.dp)) {
            Column {
                Icon(icon, contentDescription = null, tint = SurfaceLight, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = SurfaceLight)
                Text(title, fontSize = 12.sp, color = SurfaceLight.copy(alpha = 0.8f))
            }
        }
    }
}

// ─── Seller Product Card ──────────────────────────────────────────────────────

@Composable
fun SellerProductCard(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onUpdateStock: (Int) -> Unit
) {
    var showStockDialog by remember { mutableStateOf(false) }
    var stockInput      by remember { mutableStateOf(product.stock.toString()) }

    if (showStockDialog) {
        AlertDialog(
            onDismissRequest = { showStockDialog = false },
            title            = { Text("Update Stock", fontWeight = FontWeight.Bold) },
            text             = {
                OutlinedTextField(
                    value         = stockInput,
                    onValueChange = { stockInput = it },
                    label         = { Text("New stock quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine    = true
                )
            },
            confirmButton    = {
                TextButton(onClick = {
                    val s = stockInput.toIntOrNull()
                    if (s != null) onUpdateStock(s)
                    showStockDialog = false
                }) { Text("Update", fontWeight = FontWeight.Bold) }
            },
            dismissButton    = {
                TextButton(onClick = { showStockDialog = false }) { Text("Cancel") }
            }
        )
    }

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = SurfaceLight)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model              = product.imageUrl.ifBlank { "https://images.unsplash.com/photo-1490885578174-acda8905c2c6?w=200&q=80" },
                contentDescription = product.name,
                modifier           = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
                contentScale       = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PrimaryGreenDark, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("Category: ${product.category}", fontSize = 12.sp, color = TextSecondary)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Stock: ${product.stock} ${product.unit}", fontSize = 12.sp, color = if (product.stock < 5) DiscountRed else PrimaryGreen, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (product.stock > 0) PrimaryGreen.copy(alpha = 0.1f) else DiscountRed.copy(alpha = 0.1f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(if (product.stock > 0) "IN STOCK" else "OUT", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (product.stock > 0) PrimaryGreen else DiscountRed)
                    }
                }
                Text("₹${product.price.toInt()} / ${product.unit}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DiscountRed)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { showStockDialog = true }, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Inventory2, contentDescription = "Stock", tint = SecondaryOrange, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryGreen, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = DiscountRed, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
