package com.hallisanthe.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.utils.ProductImageMapper
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(cartCount: Int, onProfileClick: () -> Unit, onCartClick: () -> Unit) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text       = "Halli-Santhe",
                    color      = PrimaryGreenDark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 24.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                )
                Text(
                    text          = "TRADITIONAL ARTISAN MARKET",
                    color         = TextSecondary,
                    fontWeight    = FontWeight.Bold,
                    fontSize      = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        },
        actions = {
            Box {
                IconButton(onClick = onCartClick) {
                    Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart", tint = PrimaryGreenDark)
                }
                if (cartCount > 0) {
                    Badge(
                        modifier           = Modifier.align(Alignment.TopEnd).offset((-4).dp, 4.dp),
                        containerColor     = SecondaryOrange,
                        contentColor       = Color.White
                    ) {
                        Text(if (cartCount > 9) "9+" else cartCount.toString(), fontSize = 9.sp)
                    }
                }
            }
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Outlined.PersonOutline, contentDescription = "Profile", tint = PrimaryGreenDark)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
    )
}

@Composable
fun HomeBottomNavigation(
    currentRoute: String = "Market",
    cartCount: Int = 0,
    onNavigateToHome: () -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onNavigateToFavorites: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    NavigationBar(
        containerColor = SurfaceLight,
        contentColor   = PrimaryGreenDark,
        tonalElevation = 8.dp
    ) {
        data class NavItem(val label: String, val icon: ImageVector, val filledIcon: ImageVector, val onClick: () -> Unit)

        val items = listOf(
            NavItem("Market",    Icons.Outlined.ShoppingBag,   Icons.Filled.ShoppingBag,   onNavigateToHome),
            NavItem("Search",    Icons.Outlined.Search,         Icons.Filled.Search,         onNavigateToSearch),
            NavItem("Favorites", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite,       onNavigateToFavorites),
            NavItem("Cart",      Icons.Outlined.ShoppingCart,   Icons.Filled.ShoppingCart,   onNavigateToCart),
            NavItem("Profile",   Icons.Outlined.PersonOutline,  Icons.Filled.Person,         onNavigateToProfile)
        )

        items.forEach { item ->
            val selected = currentRoute == item.label
            NavigationBarItem(
                selected  = selected,
                onClick   = item.onClick,
                icon      = {
                    if (item.label == "Cart" && cartCount > 0) {
                        BadgedBox(badge = {
                            Badge(containerColor = DiscountRed) {
                                Text(if (cartCount > 9) "9+" else cartCount.toString(), fontSize = 9.sp)
                            }
                        }) {
                            Icon(if (selected) item.filledIcon else item.icon, contentDescription = item.label)
                        }
                    } else {
                        Icon(if (selected) item.filledIcon else item.icon, contentDescription = item.label)
                    }
                },
                label     = { Text(item.label, fontSize = 10.sp) },
                colors    = NavigationBarItemDefaults.colors(
                    selectedIconColor   = PrimaryGreenDark,
                    selectedTextColor   = PrimaryGreenDark,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = BackgroundLight.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
fun HeroBanner(onExploreClick: () -> Unit = {}) {
    Card(
        modifier  = Modifier.fillMaxWidth().padding(16.dp),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(containerColor = PrimaryGreenDark)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = "Authentic\nVillage\nGoodness",
                    color      = Color.White,
                    fontSize   = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text  = "Direct from local artisans to your doorstep.",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onExploreClick,
                    colors  = ButtonDefaults.buttonColors(containerColor = SecondaryOrange),
                    shape   = RoundedCornerShape(20.dp)
                ) {
                    Text("EXPLORE NOW", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 12.sp)
                }
            }
            // Optional: Add a 3D illustration here if needed
            Text("🌾", fontSize = 64.sp)
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String?, actionIcon: ImageVector? = null, onActionClick: () -> Unit = {}) {
    Row(
        modifier              = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
            if (actionIcon != null) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = actionIcon, contentDescription = null, tint = SecondaryOrange, modifier = Modifier.size(18.dp))
            }
        }
        if (actionText != null) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onActionClick() }) {
                Text(text = actionText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun RecommendedProductCard(
    product: com.hallisanthe.app.models.Product,
    isFavorite: Boolean,
    onAddToCart: () -> Unit,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    val favColor by androidx.compose.animation.animateColorAsState(
        targetValue    = if (isFavorite) DiscountRed else TextSecondary.copy(alpha = 0.5f),
        label          = "favColor"
    )

    Card(
        modifier = Modifier.width(170.dp).height(260.dp).padding(4.dp).clickable { onClick() },
        shape    = RoundedCornerShape(20.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(140.dp).background(Color(0xFFF9F9F9))
            ) {
                AsyncImage(
                    model            = ProductImageMapper.getIllustration(product),
                    contentDescription = product.name,
                    modifier         = Modifier.fillMaxSize().padding(16.dp),
                    contentScale     = ContentScale.Fit,
                    alignment        = Alignment.Center
                )

                // Favorite Icon
                IconButton(
                    onClick  = onFavoriteClick,
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                ) {
                    Icon(
                        imageVector  = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint         = favColor,
                        modifier     = Modifier.size(24.dp).background(Color.White.copy(alpha = 0.8f), CircleShape).padding(4.dp)
                    )
                }

                // Delivery Time Badge
                Box(
                    modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.9f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Timer, contentDescription = null, tint = SecondaryOrange, modifier = Modifier.size(10.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(product.deliveryTime, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                    }
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text     = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color    = PrimaryGreenDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = SecondaryOrange, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("${product.rating}", fontSize = 11.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("• ${product.unit}", fontSize = 11.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.weight(1f))
                
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(text = "₹${product.price.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryGreenDark)
                    
                    Surface(
                        onClick = onAddToCart,
                        modifier = Modifier.size(32.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = PrimaryGreenDark
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SmallProductRow(
    products: List<com.hallisanthe.app.models.Product>,
    favoriteIds: Set<String> = emptySet(),
    onAddToCart: (com.hallisanthe.app.models.Product) -> Unit,
    onProductClick: (com.hallisanthe.app.models.Product) -> Unit,
    onFavoriteClick: ((com.hallisanthe.app.models.Product) -> Unit)? = null
) {
    androidx.compose.foundation.lazy.LazyRow(
        contentPadding        = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products, key = { it.id }) { product ->
            SmallProductCard(
                product        = product,
                isFavorite     = favoriteIds.contains(product.id),
                onAddToCart    = { onAddToCart(product) },
                onFavoriteClick = { onFavoriteClick?.invoke(product) },
                onClick        = { onProductClick(product) }
            )
        }
    }
}

@Composable
fun SmallProductCard(
    product: com.hallisanthe.app.models.Product,
    isFavorite: Boolean = false,
    onAddToCart: () -> Unit,
    onFavoriteClick: () -> Unit = {},
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.width(120.dp).padding(4.dp).clickable { onClick() },
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp).background(Color(0xFFF9F9F9))
            ) {
                AsyncImage(
                    model            = ProductImageMapper.getIllustration(product),
                    contentDescription = product.name,
                    modifier         = Modifier.fillMaxSize().padding(12.dp),
                    contentScale     = ContentScale.Fit,
                    alignment        = Alignment.Center
                )
                
                IconButton(
                    onClick  = onFavoriteClick,
                    modifier = Modifier.size(28.dp).align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector  = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint         = if (isFavorite) DiscountRed else TextSecondary.copy(alpha = 0.4f),
                        modifier     = Modifier.size(16.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text       = product.name,
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color      = PrimaryGreenDark,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "₹${product.price.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryGreenDark)
                    Surface(
                        onClick = onAddToCart,
                        modifier = Modifier.size(24.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = SecondaryOrange.copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Add, contentDescription = "Add", tint = SecondaryOrange, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}
