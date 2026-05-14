package com.hallisanthe.app.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.utils.ProductImageMapper
import coil.compose.AsyncImage
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    product: Product?,
    cartViewModel: CartViewModel,
    favoriteViewModel: FavoriteViewModel,
    onBack: () -> Unit
) {
    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("😕", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Product not found", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBack) { Text("Go Back") }
            }
        }
        return
    }

    val favoriteIds by favoriteViewModel.favoriteIds.collectAsState()
    val isFavorite  = favoriteIds.contains(product.id)
    val context     = LocalContext.current

    var quantity by remember { mutableStateOf(1) }

    val favColor by animateColorAsState(
        targetValue   = if (isFavorite) DiscountRed else TextSecondary,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label         = "pdFavColor"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title          = { Text(product.name, fontWeight = FontWeight.Bold, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { favoriteViewModel.toggleFavorite(product) }) {
                        Icon(
                            imageVector        = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint               = favColor
                        )
                    }
                    IconButton(onClick = {
                        Toast.makeText(context, "Shared!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = TextSecondary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        bottomBar = {
            Surface(
                color          = SurfaceLight,
                shadowElevation = 16.dp,
                modifier       = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier              = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Price", color = TextSecondary, fontSize = 12.sp)
                        Text(
                            "₹${(product.price * quantity).toInt()}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize   = 20.sp,
                            color      = PrimaryGreenDark
                        )
                        if (quantity > 1) {
                            Text("₹${product.price.toInt()} × $quantity", color = TextSecondary, fontSize = 11.sp)
                        }
                    }
                    Button(
                        onClick = {
                            cartViewModel.addToCart(product, quantity)
                            Toast.makeText(context, "${product.name} added to cart!", Toast.LENGTH_SHORT).show()
                        },
                        shape  = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DiscountRed)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add to Cart", fontWeight = FontWeight.Bold, color = SurfaceLight)
                    }
                }
            }
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(SurfaceLight),
                contentAlignment = Alignment.Center
            ) {
                // Soft Glow Background for the illustration
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen.copy(alpha = 0.05f))
                )
                
                AsyncImage(
                    model              = ProductImageMapper.getIllustration(product),
                    contentDescription = product.name,
                    modifier           = Modifier.fillMaxSize().padding(32.dp),
                    contentScale       = ContentScale.Fit,
                    alignment          = Alignment.Center
                )
                if (product.discountPercent != null) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DiscountRed)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Text("${product.discountPercent}% OFF", color = SurfaceLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                if (product.tag != null) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BannerDark)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Text(product.tag, color = SurfaceLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                // Name + Rating
                Text(product.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = SecondaryOrange, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${product.rating} (120 reviews)", fontSize = 14.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Quantity Selector
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = SurfaceLight),
                    shape    = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier              = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text("Quantity", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Row(
                            verticalAlignment      = Alignment.CenterVertically,
                            modifier               = Modifier
                                .background(BackgroundLight, RoundedCornerShape(8.dp))
                                .padding(horizontal = 4.dp)
                        ) {
                            IconButton(
                                onClick  = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(18.dp), tint = PrimaryGreenDark)
                            }
                            Text(
                                text      = quantity.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize  = 18.sp,
                                modifier  = Modifier.padding(horizontal = 12.dp)
                            )
                            IconButton(
                                onClick  = { if (quantity < (product.stock.takeIf { it > 0 } ?: 99)) quantity++ },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(18.dp), tint = DiscountRed)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Seller Info
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = SurfaceLight),
                    shape    = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Store, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Seller Information", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Sold by: ${product.sellerName.ifEmpty { "Local Artisan" }}", fontSize = 14.sp, color = TextPrimary)
                        Text("Category: ${product.category.ifEmpty { "General" }}", fontSize = 14.sp, color = TextSecondary)
                        Text("Stock: ${if (product.stock > 0) "${product.stock} ${product.unit} available" else "Limited stock"}", fontSize = 14.sp, color = if (product.stock > 5) PrimaryGreen else DiscountRed)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Delivery Info
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = SurfaceLight),
                    shape    = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = SecondaryOrange, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Delivery Options", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Seller Delivery – ₹30", fontSize = 14.sp, color = TextPrimary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Self Pickup – Free", fontSize = 14.sp, color = TextPrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                Text("About Product", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text       = product.description.ifEmpty { "Authentic traditional product sourced directly from local artisans and farmers. Grown without harmful chemicals. Guaranteed quality and freshness." },
                    fontSize   = 14.sp,
                    color      = TextPrimary,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Check Stock Button
                Button(
                    onClick = {
                        Toast.makeText(context, "Stock inquiry sent to seller!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors   = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    shape    = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Inventory2, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Check Stock Availability", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(100.dp)) // Extra padding for bottom bar
            }
        }
    }
}
