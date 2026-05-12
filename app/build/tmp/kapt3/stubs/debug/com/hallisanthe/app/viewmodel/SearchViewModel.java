package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\nJ\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\nJ\u0014\u0010\u001b\u001a\u00020\u00172\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u000e\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\nR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e\u00a8\u0006\u001e"}, d2 = {"Lcom/hallisanthe/app/viewmodel/SearchViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_allProducts", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/hallisanthe/app/models/Product;", "_searchQuery", "", "filteredProducts", "Lkotlinx/coroutines/flow/StateFlow;", "getFilteredProducts", "()Lkotlinx/coroutines/flow/StateFlow;", "recentSearches", "Lcom/hallisanthe/app/room/RecentSearchEntity;", "getRecentSearches", "repository", "Lcom/hallisanthe/app/repository/SearchRepository;", "searchQuery", "getSearchQuery", "clearHistory", "", "deleteRecentSearch", "query", "onSearchExecuted", "setAllProducts", "products", "updateSearchQuery", "app_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.FlowPreview.class})
public final class SearchViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.SearchRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.hallisanthe.app.models.Product>> _allProducts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Product>> filteredProducts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.room.RecentSearchEntity>> recentSearches = null;
    
    public SearchViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Product>> getFilteredProducts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.room.RecentSearchEntity>> getRecentSearches() {
        return null;
    }
    
    public final void updateSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void setAllProducts(@org.jetbrains.annotations.NotNull()
    java.util.List<com.hallisanthe.app.models.Product> products) {
    }
    
    public final void onSearchExecuted(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void deleteRecentSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void clearHistory() {
    }
}