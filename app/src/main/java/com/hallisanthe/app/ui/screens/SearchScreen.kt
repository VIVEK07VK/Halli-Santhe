package com.hallisanthe.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.ui.components.SearchBarComponent
import com.hallisanthe.app.ui.components.HomeBottomNavigation
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.viewmodel.HomeViewModel
import com.hallisanthe.app.viewmodel.SearchViewModel
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.FavoriteViewModel
import com.hallisanthe.app.utils.ProductImageMapper

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    favoriteViewModel: FavoriteViewModel,
    onProductClick: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val searchQuery by searchViewModel.searchQuery.collectAsState()
    val filteredProducts by searchViewModel.filteredProducts.collectAsState()
    val recentSearches by searchViewModel.recentSearches.collectAsState()
    val allProducts by homeViewModel.allProducts.collectAsState()
    val favoriteIds by favoriteViewModel.favoriteIds.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartCount = cartItems.sumOf { it.quantity }

    // Pass all products to search view model once
    LaunchedEffect(allProducts) {
        searchViewModel.setAllProducts(allProducts)
    }

    Scaffold(
        containerColor = BackgroundLight,
        bottomBar = {
            HomeBottomNavigation(
                currentRoute = "Search",
                cartCount = cartCount,
                onNavigateToHome = onNavigateToHome,
                onNavigateToSearch = onNavigateToSearch,
                onNavigateToFavorites = onNavigateToFavorites,
                onNavigateToCart = onNavigateToCart,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            SearchBarComponent(
                query = searchQuery,
                onQueryChange = { searchViewModel.updateSearchQuery(it) },
                onSearchExecuted = { searchViewModel.onSearchExecuted(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = searchQuery.isEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    if (recentSearches.isNotEmpty()) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Recent Searches", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
                            TextButton(onClick = { searchViewModel.clearHistory() }) {
                                Text("Clear", color = DiscountRed, fontSize = 12.sp)
                            }
                        }
                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            recentSearches.take(8).forEach { recent ->
                                SearchChip(recent.query) { 
                                    searchViewModel.updateSearchQuery(recent.query)
                                    searchViewModel.onSearchExecuted(recent.query)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    TrendingSuggestionCard {
                        val trendingQuery = "Handcrafted Wooden Toys"
                        searchViewModel.updateSearchQuery(trendingQuery)
                        searchViewModel.onSearchExecuted(trendingQuery)
                    }
                }
            }

            // Results Section
            AnimatedVisibility(
                visible = searchQuery.isNotEmpty(),
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut()
            ) {
                if (filteredProducts.isEmpty()) {
                    EmptySearchState()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text("Showing ${filteredProducts.size} results", fontSize = 12.sp, color = TextSecondary)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        items(filteredProducts, key = { it.id }) { product ->
                            SearchResultItem(
                                product = product,
                                isFavorite = favoriteIds.contains(product.id),
                                onAddToCart = { cartViewModel.addToCart(product) },
                                onFavoriteClick = { favoriteViewModel.toggleFavorite(product) },
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchChip(label: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, BackgroundLight),
        shadowElevation = 1.dp
    ) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, fontSize = 13.sp, color = TextPrimary)
        }
    }
}

@Composable
fun TrendingSuggestionCard(onTrySearching: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8F0)), // Creamy beige
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SecondaryOrange, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("SUGGESTED FOR TODAY", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SecondaryOrange, letterSpacing = 1.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Handcrafted Wooden Toys from Channapatna are trending!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreenDark,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.clickable { onTrySearching() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("TRY SEARCHING", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp), tint = PrimaryGreen)
            }
        }
    }
}

@Composable
fun SearchResultItem(
    product: Product,
    isFavorite: Boolean,
    onAddToCart: () -> Unit,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BackgroundLight.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ProductImageMapper.getIllustration(product),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PrimaryGreenDark)
                Text(product.sellerName, fontSize = 12.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("₹${product.price.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = PrimaryGreen)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) DiscountRed else Color.LightGray
                    )
                }
                IconButton(onClick = onAddToCart, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.AddCircleOutline, contentDescription = null, tint = DiscountRed)
                }
            }
        }
    }
}

@Composable
fun EmptySearchState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.SearchOff, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))
            Text("No matching products found", color = TextSecondary, textAlign = TextAlign.Center)
            Text("Try searching for different keywords", fontSize = 12.sp, color = Color.Gray)
        }
    }
}
