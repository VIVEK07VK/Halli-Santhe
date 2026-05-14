package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J8\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\u00072\u0006\u0010)\u001a\u00020\u00072\b\b\u0002\u0010*\u001a\u00020\u0007J\u000e\u0010+\u001a\u00020#2\u0006\u0010,\u001a\u00020\u0007J\u0006\u0010-\u001a\u00020#J\u0016\u0010.\u001a\u00020#2\u0006\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u0007J\u0016\u00101\u001a\u00020#2\u0006\u00102\u001a\u00020\u00072\u0006\u00103\u001a\u00020\u0007J\u000e\u00104\u001a\u00020#2\u0006\u00105\u001a\u00020\u001eJ\u0016\u00106\u001a\u00020#2\u0006\u00105\u001a\u00020\u001e2\u0006\u00107\u001a\u00020\'R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\rR\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00170\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\rR\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00170\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\rR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\r\u00a8\u00068"}, d2 = {"Lcom/hallisanthe/app/viewmodel/SellerViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_currentUserId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_totalRevenue", "", "currentUserId", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentUserId", "()Lkotlinx/coroutines/flow/StateFlow;", "inquiryRepository", "Lcom/hallisanthe/app/repository/InquiryRepository;", "orderRepository", "Lcom/hallisanthe/app/repository/OrderRepository;", "productDao", "Lcom/hallisanthe/app/room/ProductDao;", "productRepository", "Lcom/hallisanthe/app/repository/ProductRepository;", "sellerInquiries", "", "Lcom/hallisanthe/app/models/Inquiry;", "getSellerInquiries", "sellerOrders", "Lcom/hallisanthe/app/models/Order;", "getSellerOrders", "sellerProducts", "Lcom/hallisanthe/app/models/Product;", "getSellerProducts", "totalRevenue", "getTotalRevenue", "addProduct", "", "name", "price", "stock", "", "category", "imageUrl", "unit", "deleteProduct", "productId", "refreshProducts", "respondToInquiry", "inquiryId", "status", "updateOrderStatus", "orderId", "newStatus", "updateProduct", "product", "updateProductStock", "newStock", "app_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class SellerViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.room.ProductDao productDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.ProductRepository productRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.OrderRepository orderRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.InquiryRepository inquiryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _currentUserId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> currentUserId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Product>> sellerProducts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Double> _totalRevenue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Double> totalRevenue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Order>> sellerOrders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Inquiry>> sellerInquiries = null;
    
    public SellerViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getCurrentUserId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Product>> getSellerProducts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Double> getTotalRevenue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Order>> getSellerOrders() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Inquiry>> getSellerInquiries() {
        return null;
    }
    
    /**
     * Add a new product. unit param added.
     */
    public final void addProduct(@org.jetbrains.annotations.NotNull()
    java.lang.String name, double price, int stock, @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    java.lang.String imageUrl, @org.jetbrains.annotations.NotNull()
    java.lang.String unit) {
    }
    
    /**
     * Update full product (e.g. from edit dialog).
     */
    public final void updateProduct(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product) {
    }
    
    /**
     * Update just the stock quantity.
     */
    public final void updateProductStock(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, int newStock) {
    }
    
    public final void deleteProduct(@org.jetbrains.annotations.NotNull()
    java.lang.String productId) {
    }
    
    public final void updateOrderStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String newStatus) {
    }
    
    public final void respondToInquiry(@org.jetbrains.annotations.NotNull()
    java.lang.String inquiryId, @org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    public final void refreshProducts() {
    }
}