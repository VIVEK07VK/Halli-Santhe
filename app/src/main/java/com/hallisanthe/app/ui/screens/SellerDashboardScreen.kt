package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.hallisanthe.app.ui.components.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(
    sellerViewModel: SellerViewModel,
    earningsViewModel: EarningsViewModel = viewModel(),
    onNavigateToOrders: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAddProduct: () -> Unit,
    onLogout: () -> Unit
) {
    val sellerProducts  by sellerViewModel.sellerProducts.collectAsState()
    val sellerInquiries by sellerViewModel.sellerInquiries.collectAsState()
    val orders          by sellerViewModel.sellerOrders.collectAsState()
    val earningsSummary by earningsViewModel.earningsSummary.collectAsState()

    val pendingOrdersCount = orders.count {
        it.orderStatus in listOf("PENDING", "WAITING_CONFIRMATION", "ACCEPTED", "PREPARING", "READY_FOR_PICKUP")
    }

    // Edit Product Dialog state
    var editProduct    by remember { mutableStateOf<Product?>(null) }

    if (editProduct != null) {
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
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Outlined.PersonOutline, contentDescription = "Profile", tint = PrimaryGreenDark)
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

            // ── Manage Orders Button ──────────────────────────────────────────────
            item {
                Button(
                    onClick = onNavigateToOrders,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = PrimaryGreenDark)
                ) {
                    Icon(Icons.Default.ListAlt, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Manage Real-time Orders", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            // ── Products List Header ─────────────────────────────────────────────
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Your Product Catalog", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
                    TextButton(onClick = { sellerViewModel.refreshProducts() }) {
                        Text("Refresh", color = PrimaryGreen)
                    }
                    TextButton(onClick = onNavigateToProducts) {
                        Text("View All", color = SecondaryOrange)
                    }
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
                items(sellerProducts.take(10), key = { it.id }) { product ->
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

// Re-using the StatCard and SellerProductCard from previous implementations or local definitions
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
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text(title, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}

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
                AppTextField(
                    value         = stockInput,
                    onValueChange = { stockInput = it },
                    label         = "New stock quantity",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth().verticalScroll(rememberScrollState())) {
                Text(
                    if (existingProduct == null) "Add New Product" else "Edit Product",
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                    color      = PrimaryGreenDark
                )
                Spacer(modifier = Modifier.height(16.dp))

                AppTextField(value = name,     onValueChange = { name = it },     label = "Product Name",    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(value = price,    onValueChange = { price = it },    label = "Price (₹)",       modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(value = stock,    onValueChange = { stock = it },    label = "Stock Quantity",  modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(value = unit,     onValueChange = { unit = it },     label = "Unit (kg/pc/jar)", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = "Image URL", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(expanded = catExpanded, onExpandedChange = { catExpanded = !catExpanded }) {
                    AppTextField(
                        value           = category,
                        onValueChange   = {},
                        readOnly        = true,
                        label           = "Category",
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
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) { Text("Cancel") }
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
