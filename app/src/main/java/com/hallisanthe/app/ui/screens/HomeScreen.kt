package com.hallisanthe.app.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import coil.compose.AsyncImage
import com.hallisanthe.app.models.Category
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.ui.components.*
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.AuthViewModel
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.FavoriteViewModel
import com.hallisanthe.app.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerHomeScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    favoriteViewModel: FavoriteViewModel,
    onNavigateToCart: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onProductClick: (String) -> Unit,
    onLogout: () -> Unit
) {
    val recommendedProducts by homeViewModel.recommendedProducts.collectAsState()
    val bestSellers       by homeViewModel.bestSellers.collectAsState()
    val freshArrivals     by homeViewModel.freshArrivals.collectAsState()
    val trendingProducts  by homeViewModel.trendingProducts.collectAsState()
    val categories        by homeViewModel.categories.collectAsState()
    val recentlyViewed    by homeViewModel.recentlyViewed.collectAsState()
    val cartItems         by cartViewModel.cartItems.collectAsState()
    val favoriteIds       by favoriteViewModel.favoriteIds.collectAsState()
    val selectedCategory by homeViewModel.selectedCategory.collectAsState()

    val cartCount = cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                cartCount     = cartCount,
                onProfileClick = onNavigateToProfile,
                onCartClick    = onNavigateToCart
            )
        },
        bottomBar = {
            HomeBottomNavigation(
                currentRoute         = "Market",
                cartCount            = cartCount,
                onNavigateToHome     = { },
                onNavigateToSearch   = onNavigateToSearch,
                onNavigateToFavorites = onNavigateToFavorites,
                onNavigateToCart     = onNavigateToCart,
                onNavigateToProfile  = onNavigateToProfile
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            HeroBanner(onExploreClick = onNavigateToSearch)

            SectionHeader(title = "Discover Goods", actionText = null)
            CategoryFilterRow(
                categories       = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { homeViewModel.selectCategory(it) }
            )

            SectionHeader(
                title      = if (selectedCategory == "All") "Recommended for You" else "Best in $selectedCategory",
                actionText = null,
                actionIcon = Icons.Outlined.AutoAwesome
            )
            
            androidx.compose.animation.Crossfade(targetState = recommendedProducts) { filteredList ->
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredList) { product ->
                        RecommendedProductCard(
                            product        = product,
                            isFavorite     = favoriteIds.contains(product.id),
                            onAddToCart    = { cartViewModel.addToCart(product) },
                            onFavoriteClick = { favoriteViewModel.toggleFavorite(product) },
                            onClick        = {
                                homeViewModel.addToRecentlyViewed(product)
                                onProductClick(product.id)
                            }
                        )
                    }
                    if (filteredList.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillParentMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                                Text("No products found in this category", color = TextSecondary)
                            }
                        }
                    }
                }
            }

            SectionHeader(title = "Best Sellers 🌟", actionText = "See All", onActionClick = onNavigateToSearch)
            SmallProductRow(
                products       = bestSellers,
                favoriteIds    = favoriteIds,
                onAddToCart    = { cartViewModel.addToCart(it) },
                onProductClick = {
                    homeViewModel.addToRecentlyViewed(it)
                    onProductClick(it.id)
                },
                onFavoriteClick = { favoriteViewModel.toggleFavorite(it) }
            )

            SectionHeader(title = "Fresh Arrivals", actionText = "See All", onActionClick = onNavigateToSearch)
            SmallProductRow(
                products       = freshArrivals,
                favoriteIds    = favoriteIds,
                onAddToCart    = { cartViewModel.addToCart(it) },
                onProductClick = {
                    homeViewModel.addToRecentlyViewed(it)
                    onProductClick(it.id)
                },
                onFavoriteClick = { favoriteViewModel.toggleFavorite(it) }
            )

            SectionHeader(title = "Trending Products 🔥", actionText = "See All", onActionClick = onNavigateToSearch)
            SmallProductRow(
                products       = trendingProducts,
                favoriteIds    = favoriteIds,
                onAddToCart    = { cartViewModel.addToCart(it) },
                onProductClick = {
                    homeViewModel.addToRecentlyViewed(it)
                    onProductClick(it.id)
                },
                onFavoriteClick = { favoriteViewModel.toggleFavorite(it) }
            )

            SectionHeader(title = "Recently Viewed", actionText = "See All", onActionClick = onNavigateToSearch)
            SmallProductRow(
                products       = recentlyViewed,
                favoriteIds    = favoriteIds,
                onAddToCart    = { cartViewModel.addToCart(it) },
                onProductClick = { onProductClick(it.id) },
                onFavoriteClick = { favoriteViewModel.toggleFavorite(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

