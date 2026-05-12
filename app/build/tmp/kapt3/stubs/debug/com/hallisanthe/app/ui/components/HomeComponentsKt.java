package com.hallisanthe.app.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001al\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a,\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001aB\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a6\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001aF\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001af\u0010 \u001a\u00020\u00012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00130\"2\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060$2\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010%2\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0001\u0018\u00010%H\u0007\u00a8\u0006\'"}, d2 = {"HeroBanner", "", "onExploreClick", "Lkotlin/Function0;", "HomeBottomNavigation", "currentRoute", "", "cartCount", "", "onNavigateToHome", "onNavigateToSearch", "onNavigateToFavorites", "onNavigateToCart", "onNavigateToProfile", "HomeTopAppBar", "onProfileClick", "onCartClick", "RecommendedProductCard", "product", "Lcom/hallisanthe/app/models/Product;", "isFavorite", "", "onAddToCart", "onFavoriteClick", "onClick", "SectionHeader", "title", "actionText", "actionIcon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "onActionClick", "SmallProductCard", "SmallProductRow", "products", "", "favoriteIds", "", "Lkotlin/Function1;", "onProductClick", "app_debug"})
public final class HomeComponentsKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeTopAppBar(int cartCount, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onProfileClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCartClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HomeBottomNavigation(@org.jetbrains.annotations.NotNull()
    java.lang.String currentRoute, int cartCount, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSearch, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToFavorites, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HeroBanner(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onExploreClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SectionHeader(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    java.lang.String actionText, @org.jetbrains.annotations.Nullable()
    androidx.compose.ui.graphics.vector.ImageVector actionIcon, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onActionClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RecommendedProductCard(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, boolean isFavorite, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFavoriteClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SmallProductRow(@org.jetbrains.annotations.NotNull()
    java.util.List<com.hallisanthe.app.models.Product> products, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> favoriteIds, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.hallisanthe.app.models.Product, kotlin.Unit> onAddToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.hallisanthe.app.models.Product, kotlin.Unit> onProductClick, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super com.hallisanthe.app.models.Product, kotlin.Unit> onFavoriteClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SmallProductCard(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, boolean isFavorite, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFavoriteClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}