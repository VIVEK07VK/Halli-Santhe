package com.hallisanthe.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.SellerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductsScreen(
    onBack: () -> Unit,
    onNavigateToAddProduct: () -> Unit,
    sellerViewModel: SellerViewModel = viewModel()
) {
    val sellerProducts by sellerViewModel.sellerProducts.collectAsState()
    var editProduct by remember { mutableStateOf<Product?>(null) }

    if (editProduct != null) {
        AddEditProductDialog(
            existingProduct = editProduct,
            onDismiss = { editProduct = null },
            onSave = { name, price, stock, category, imageUrl, unit ->
                sellerViewModel.updateProduct(editProduct!!.copy(name = name, price = price, stock = stock, category = category, imageUrl = imageUrl, unit = unit))
                editProduct = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Products", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToAddProduct) {
                        Icon(Icons.Default.Add, contentDescription = "Add Product", tint = PrimaryGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF9F4EE)
    ) { padding ->
        if (sellerProducts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📦", fontSize = 64.sp)
                    Spacer(Modifier.height(16.dp))
                    Text("No products uploaded yet", fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = onNavigateToAddProduct,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Add Your First Product")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sellerProducts, key = { it.id }) { product ->
                    SellerProductCard(
                        product = product,
                        onEdit = { editProduct = product },
                        onDelete = { sellerViewModel.deleteProduct(product.id) },
                        onUpdateStock = { newStock -> sellerViewModel.updateProductStock(product, newStock) }
                    )
                }
            }
        }
    }
}
