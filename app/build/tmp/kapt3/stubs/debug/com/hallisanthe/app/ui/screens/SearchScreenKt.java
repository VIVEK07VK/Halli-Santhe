package com.hallisanthe.app.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0007\u001a\u001e\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001aB\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a\u0082\u0001\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a\u0016\u0010\u001e\u001a\u00020\u00012\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u00a8\u0006 "}, d2 = {"EmptySearchState", "", "SearchChip", "label", "", "onClick", "Lkotlin/Function0;", "SearchResultItem", "product", "Lcom/hallisanthe/app/models/Product;", "isFavorite", "", "onAddToCart", "onFavoriteClick", "SearchScreen", "searchViewModel", "Lcom/hallisanthe/app/viewmodel/SearchViewModel;", "homeViewModel", "Lcom/hallisanthe/app/viewmodel/HomeViewModel;", "cartViewModel", "Lcom/hallisanthe/app/viewmodel/CartViewModel;", "favoriteViewModel", "Lcom/hallisanthe/app/viewmodel/FavoriteViewModel;", "onProductClick", "Lkotlin/Function1;", "onNavigateToHome", "onNavigateToSearch", "onNavigateToFavorites", "onNavigateToCart", "onNavigateToProfile", "TrendingSuggestionCard", "onTrySearching", "app_debug"})
public final class SearchScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    public static final void SearchScreen(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.SearchViewModel searchViewModel, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.HomeViewModel homeViewModel, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.CartViewModel cartViewModel, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.FavoriteViewModel favoriteViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onProductClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHome, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSearch, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToFavorites, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SearchChip(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TrendingSuggestionCard(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTrySearching) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SearchResultItem(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, boolean isFavorite, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddToCart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFavoriteClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptySearchState() {
    }
}