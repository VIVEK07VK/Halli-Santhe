package com.hallisanthe.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hallisanthe.app.models.FavoriteEntity
import com.hallisanthe.app.ui.components.HomeBottomNavigation
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteViewModel: FavoriteViewModel,
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onProductClick: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val favorites  by favoriteViewModel.favorites.collectAsState()
    val cartItems  by cartViewModel.cartItems.collectAsState()
    val cartCount  = cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title          = { Text("My Favorites ❤️", fontWeight = FontWeight.Bold, color = PrimaryGreenDark) },
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
                currentRoute          = "Favorites",
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
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Empty state
            AnimatedVisibility(
                visible = favorites.isEmpty(),
                enter   = fadeIn(),
                exit    = fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("💔", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No Favorites Yet", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = PrimaryGreenDark)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tap the ❤️ on any product to save it here", color = TextSecondary, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = onNavigateToHome,
                            colors  = ButtonDefaults.buttonColors(containerColor = DiscountRed),
                            shape   = RoundedCornerShape(12.dp)
                        ) {
                            Text("Explore Products", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Favorites list
            AnimatedVisibility(
                visible  = favorites.isNotEmpty(),
                enter    = fadeIn(),
                exit     = fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier        = Modifier.fillMaxSize(),
                    contentPadding  = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            "${favorites.size} saved item${if (favorites.size != 1) "s" else ""}",
                            color      = TextSecondary,
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    items(favorites, key = { it.productId }) { fav ->
                        FavoriteItemCard(
                            favorite    = fav,
                            onAddToCart = { cartViewModel.addToCart(fav.toProduct()) },
                            onRemove    = { favoriteViewModel.removeFavorite(fav.productId) },
                            onClick     = { onProductClick(fav.productId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteItemCard(
    favorite: FavoriteEntity,
    onAddToCart: () -> Unit,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier  = Modifier.fillMaxWidth().clickable { onClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = SurfaceLight),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model              = favorite.imageUrl,
                contentDescription = favorite.name,
                modifier           = Modifier.size(80.dp).clip(RoundedCornerShape(10.dp)),
                contentScale       = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(favorite.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryGreenDark, maxLines = 1, overflow = TextOverflow.Ellipsis)
                if (favorite.category.isNotEmpty()) {
                    Text(favorite.category, fontSize = 12.sp, color = TextSecondary)
                }
                if (favorite.sellerName.isNotEmpty()) {
                    Text("by ${favorite.sellerName}", fontSize = 11.sp, color = TextSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("₹${favorite.price.toInt()} / ${favorite.unit}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DiscountRed)
                Text(
                    text  = "⭐ ${favorite.rating}",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onRemove, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector        = Icons.Default.Favorite,
                        contentDescription = "Remove from favorites",
                        tint               = DiscountRed,
                        modifier           = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                FilledIconButton(
                    onClick  = onAddToCart,
                    modifier = Modifier.size(36.dp),
                    colors   = IconButtonDefaults.filledIconButtonColors(containerColor = PrimaryGreen)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add to Cart", tint = SurfaceLight, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
