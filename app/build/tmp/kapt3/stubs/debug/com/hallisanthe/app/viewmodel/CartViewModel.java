package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020%J\u001e\u0010&\u001a\u00020\u00142\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010(\u001a\u00020\u0007H\u0002J\"\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020\u00072\b\b\u0002\u0010+\u001a\u00020\u00072\b\b\u0002\u0010,\u001a\u00020\u0007J\u0006\u0010-\u001a\u00020!J\u000e\u0010.\u001a\u00020!2\u0006\u0010/\u001a\u00020\u0007J\u0006\u00100\u001a\u00020!J\u000e\u00101\u001a\u00020!2\u0006\u00102\u001a\u00020\u0007J\u0016\u00103\u001a\u00020!2\u0006\u0010/\u001a\u00020\u00072\u0006\u0010$\u001a\u00020%R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/hallisanthe/app/viewmodel/CartViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_deliveryType", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_lastPaymentMethod", "_paymentState", "Lcom/hallisanthe/app/viewmodel/PaymentState;", "cartDao", "Lcom/hallisanthe/app/room/CartDao;", "cartItems", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/hallisanthe/app/models/CartItem;", "getCartItems", "()Lkotlinx/coroutines/flow/StateFlow;", "cartSummary", "Lcom/hallisanthe/app/viewmodel/CartSummary;", "getCartSummary", "deliveryType", "getDeliveryType", "lastPaymentMethod", "getLastPaymentMethod", "orderRepository", "Lcom/hallisanthe/app/repository/OrderRepository;", "paymentState", "getPaymentState", "repository", "Lcom/hallisanthe/app/repository/CartRepository;", "addToCart", "", "product", "Lcom/hallisanthe/app/models/Product;", "quantity", "", "calculateSummary", "items", "dType", "checkoutAndCreateOrder", "paymentMethod", "transactionId", "paymentStatus", "clearCart", "removeItem", "productId", "resetPaymentState", "setDeliveryType", "type", "updateQuantity", "app_debug"})
public final class CartViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.room.CartDao cartDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.CartRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.OrderRepository orderRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.CartItem>> cartItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _deliveryType = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> deliveryType = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.viewmodel.PaymentState> _paymentState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.PaymentState> paymentState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _lastPaymentMethod = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> lastPaymentMethod = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.CartSummary> cartSummary = null;
    
    public CartViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.CartItem>> getCartItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getDeliveryType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.PaymentState> getPaymentState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getLastPaymentMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.CartSummary> getCartSummary() {
        return null;
    }
    
    public final void setDeliveryType(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
    }
    
    private final com.hallisanthe.app.viewmodel.CartSummary calculateSummary(java.util.List<com.hallisanthe.app.models.CartItem> items, java.lang.String dType) {
        return null;
    }
    
    public final void checkoutAndCreateOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, @org.jetbrains.annotations.NotNull()
    java.lang.String transactionId, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentStatus) {
    }
    
    public final void resetPaymentState() {
    }
    
    public final void addToCart(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, int quantity) {
    }
    
    public final void updateQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, int quantity) {
    }
    
    public final void removeItem(@org.jetbrains.annotations.NotNull()
    java.lang.String productId) {
    }
    
    public final void clearCart() {
    }
}