package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J.\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\u001cJ\u000e\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u0011J\u0006\u0010#\u001a\u00020\u001aJ\u000e\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u001cJ\b\u0010&\u001a\u00020\u001aH\u0002J8\u0010\'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\u001c2\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\u001c2\u0006\u0010+\u001a\u00020\u001c2\b\u0010,\u001a\u0004\u0018\u00010-R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f\u00a8\u0006."}, d2 = {"Lcom/hallisanthe/app/viewmodel/ProfileViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_userProfile", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/hallisanthe/app/models/UserModel;", "addressRepository", "Lcom/hallisanthe/app/repository/AddressRepository;", "addresses", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/hallisanthe/app/models/Address;", "getAddresses", "()Lkotlinx/coroutines/flow/StateFlow;", "recentlyViewed", "Lcom/hallisanthe/app/models/RecentlyViewedProduct;", "getRecentlyViewed", "recentlyViewedDao", "Lcom/hallisanthe/app/room/RecentlyViewedDao;", "recentlyViewedRepository", "Lcom/hallisanthe/app/repository/RecentlyViewedRepository;", "userProfile", "getUserProfile", "addAddress", "", "title", "", "village", "landmark", "pincode", "phone", "addProductToRecent", "product", "clearHistory", "deleteAddress", "addressId", "loadUserProfile", "updateProfile", "fullName", "shopName", "businessAddress", "villageName", "imageUri", "Landroid/net/Uri;", "app_debug"})
public final class ProfileViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.AddressRepository addressRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.room.RecentlyViewedDao recentlyViewedDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.RecentlyViewedRepository recentlyViewedRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.models.UserModel> _userProfile = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserModel> userProfile = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Address>> addresses = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.RecentlyViewedProduct>> recentlyViewed = null;
    
    public ProfileViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserModel> getUserProfile() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Address>> getAddresses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.RecentlyViewedProduct>> getRecentlyViewed() {
        return null;
    }
    
    private final void loadUserProfile() {
    }
    
    public final void updateProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String shopName, @org.jetbrains.annotations.NotNull()
    java.lang.String businessAddress, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String villageName, @org.jetbrains.annotations.Nullable()
    android.net.Uri imageUri) {
    }
    
    public final void addAddress(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String village, @org.jetbrains.annotations.NotNull()
    java.lang.String landmark, @org.jetbrains.annotations.NotNull()
    java.lang.String pincode, @org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    public final void deleteAddress(@org.jetbrains.annotations.NotNull()
    java.lang.String addressId) {
    }
    
    public final void addProductToRecent(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.RecentlyViewedProduct product) {
    }
    
    public final void clearHistory() {
    }
}