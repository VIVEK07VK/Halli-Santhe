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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(cartCount: Int, onProfileClick: () -> Unit, onCartClick: () -> Unit) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text       = "Halli-Santhe",
                    color      = DiscountRed,
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
                    Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart", tint = DiscountRed)
                }
                if (cartCount > 0) {
                    Badge(
                        modifier           = Modifier.align(Alignment.TopEnd).offset((-4).dp, 4.dp),
                        containerColor     = SecondaryOrange,
                        contentColor       = SurfaceLight
                    ) {
                        Text(if (cartCount > 9) "9+" else cartCount.toString(), fontSize = 9.sp)
                    }
                }
            }
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Outlined.PersonOutline, contentDescription = "Profile", tint = DiscountRed)
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
                    selectedIconColor   = DiscountRed,
                    selectedTextColor   = DiscountRed,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = BackgroundLight
                )
            )
        }
    }
}

// ─── Hero Banner ──────────────────────────────────────────────────────────────

@Composable
fun HeroBanner(onExploreClick: () -> Unit = {}) {
    Card(
        modifier  = Modifier.fillMaxWidth().padding(16.dp),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(containerColor = BannerDark)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text       = "Authentic\nVillage\nGoodness",
                color      = SurfaceLight,
                fontSize   = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text  = "Direct from local artisans to\nyour doorstep.",
                color = SurfaceLight.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onExploreClick,
                colors  = ButtonDefaults.buttonColors(containerColor = SecondaryOrange),
                shape   = RoundedCornerShape(20.dp)
            ) {
                Text("EXPLORE NOW", fontWeight = FontWeight.Bold, color = BannerDark, fontSize = 12.sp)
            }
        }
    }
}

// ─── Section Header ───────────────────────────────────────────────────────────

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

// ─── Recommended Product Card (large) ────────────────────────────────────────

@Composable
fun RecommendedProductCard(
    product: com.hallisanthe.app.models.Product,
    isFavorite: Boolean,
    onAddToCart: () -> Unit,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    val favColor by androidx.compose.animation.animateColorAsState(
        targetValue    = if (isFavorite) DiscountRed else TextSecondary,
        animationSpec  = androidx.compose.animation.core.spring(stiffness = androidx.compose.animation.core.Spring.StiffnessMedium),
        label          = "favColor"
    )

    Card(
        modifier = Modifier.width(160.dp).height(230.dp).clickable { onClick() },
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = SurfaceLight)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth().height(130.dp).background(androidx.compose.ui.graphics.Color(0xFFF5F5F5))
                ) {
                    coil.compose.AsyncImage(
                        model            = product.imageUrl,
                        contentDescription = product.name,
                        modifier         = Modifier.fillMaxSize(),
                        contentScale     = ContentScale.Crop
                    )

                    // Badges
                    Column(modifier = Modifier.padding(8.dp)) {
                        if (product.discountPercent != null) {
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                    .background(DiscountRed)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("${product.discountPercent}% OFF", color = SurfaceLight, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        if (product.tag != null) {
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                    .background(BannerDark)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(product.tag, color = SurfaceLight, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Favorite Icon – FUNCTIONAL
                    IconButton(
                        onClick  = onFavoriteClick,
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector  = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint         = favColor,
                            modifier     = Modifier.size(24.dp).background(SurfaceLight, CircleShape).padding(4.dp)
                        )
                    }

                    // Rating
                    Box(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
                            .clip(RoundedCornerShape(12.dp)).background(SurfaceLight)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Star, contentDescription = null, tint = SecondaryOrange, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("${product.rating}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
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
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = "₹${product.price.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryGreenDark)
                            Text(text = " / ${product.unit}", fontSize = 10.sp, color = TextSecondary, modifier = Modifier.padding(bottom = 2.dp))
                        }
                        IconButton(onClick = onAddToCart, modifier = Modifier.size(28.dp)) {
                            Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = "Add", tint = DiscountRed, modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }
}

// ─── Small Product Row & Card ─────────────────────────────────────────────────

@Composable
fun SmallProductRow(
    products: List<com.hallisanthe.app.models.Product>,
    favoriteIds: Set<String> = emptySet(),
    onAddToCart: (com.hallisanthe.app.models.Product) -> Unit,
    onProductClick: (com.hallisanthe.app.models.Product) -> Unit,
    onFavoriteClick: ((com.hallisanthe.app.models.Product) -> Unit)? = null
) {
    androidx.compose.foundation.lazy.LazyRow(
        contentPadding        = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
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
    val favColor by androidx.compose.animation.animateColorAsState(
        targetValue   = if (isFavorite) DiscountRed else TextSecondary,
        animationSpec = androidx.compose.animation.core.spring(stiffness = androidx.compose.animation.core.Spring.StiffnessMedium),
        label         = "favColorSmall"
    )

    Card(
        modifier = Modifier.width(110.dp).clickable { onClick() },
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = SurfaceLight)
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth().height(90.dp).background(androidx.compose.ui.graphics.Color(0xFFF5F5F5))
            ) {
                coil.compose.AsyncImage(
                    model            = product.imageUrl,
                    contentDescription = product.name,
                    modifier         = Modifier.fillMaxSize(),
                    contentScale     = ContentScale.Crop
                )
                // Fav icon top-end
                IconButton(
                    onClick  = onFavoriteClick,
                    modifier = Modifier.size(26.dp).align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector  = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint         = favColor,
                        modifier     = Modifier.size(16.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text       = product.name,
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = PrimaryGreenDark,
                    maxLines   = 1,
                    overflow   = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "₹${product.price.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                    IconButton(onClick = onAddToCart, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.AddCircleOutline, contentDescription = "Add", tint = DiscountRed, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
