package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00020\bJ\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/hallisanthe/app/viewmodel/FavoriteViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_favoriteIds", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "favoriteIds", "Lkotlinx/coroutines/flow/StateFlow;", "getFavoriteIds", "()Lkotlinx/coroutines/flow/StateFlow;", "favorites", "", "Lcom/hallisanthe/app/models/FavoriteEntity;", "getFavorites", "repository", "Lcom/hallisanthe/app/repository/FavoriteRepository;", "isFavorite", "", "productId", "removeFavorite", "", "toggleFavorite", "product", "Lcom/hallisanthe/app/models/Product;", "app_debug"})
public final class FavoriteViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.FavoriteRepository repository = null;
    
    /**
     * Live list of all favorited products (persists across restarts).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.FavoriteEntity>> favorites = null;
    
    /**
     * Set of productIds that are currently favorited — fast O(1) lookup in UI.
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _favoriteIds = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> favoriteIds = null;
    
    public FavoriteViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    /**
     * Live list of all favorited products (persists across restarts).
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.FavoriteEntity>> getFavorites() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getFavoriteIds() {
        return null;
    }
    
    /**
     * Toggle favorite state for [product].
     * Instantly updates [favoriteIds] for responsive UI, then persists to Room.
     */
    public final void toggleFavorite(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product) {
    }
    
    public final boolean isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String productId) {
        return false;
    }
    
    public final void removeFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String productId) {
    }
}