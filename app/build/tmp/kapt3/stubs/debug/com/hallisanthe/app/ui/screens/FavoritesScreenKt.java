package com.hallisanthe.app.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0080\u0001\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0016"}, d2 = {"FavoriteItemCard", "", "favorite", "Lcom/hallisanthe/app/models/FavoriteEntity;", "onAddToCart", "Lkotlin/Function0;", "onRemove", "onClick", "FavoritesScreen", "favoriteViewModel", "Lcom/hallisanthe/app/viewmodel/FavoriteViewModel;", "cartViewModel", "Lcom/hallisanthe/app/viewmodel/CartViewModel;", "onBack", "onProductClick", "Lkotlin/Function1;", "", "onNavigateToHome", "onNavigateToSearch", "onNavigateToFavorites", "onNavigateToCart", "onNavigateToProfile", "app_debug"})
public final class FavoritesScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void FavoritesScreen(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.FavoriteViewModel favoriteViewModel, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.CartViewModel cartViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onProductClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSearch, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToFavorites, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FavoriteItemCard(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.FavoriteEntity favorite, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}