package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0019J\u000e\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0019J\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010!\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\"\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010#\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0016\u0010$\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e\u00a8\u0006\'"}, d2 = {"Lcom/hallisanthe/app/viewmodel/OrdersViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_buyerOrders", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/hallisanthe/app/models/Order;", "_currentTrackingOrder", "_isLoading", "", "_sellerOrders", "buyerOrders", "Lkotlinx/coroutines/flow/StateFlow;", "getBuyerOrders", "()Lkotlinx/coroutines/flow/StateFlow;", "currentTrackingOrder", "getCurrentTrackingOrder", "isLoading", "repository", "Lcom/hallisanthe/app/repository/OrderRepository;", "sellerOrders", "getSellerOrders", "acceptOrder", "", "orderId", "", "listenToBuyerOrders", "buyerId", "listenToSellerOrders", "sellerId", "markDelivered", "markOutForDelivery", "markPreparing", "markReady", "rejectOrder", "startTracking", "updateStatus", "newStatus", "Lcom/hallisanthe/app/models/OrderStatus;", "app_debug"})
public final class OrdersViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.OrderRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.hallisanthe.app.models.Order>> _sellerOrders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Order>> sellerOrders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.hallisanthe.app.models.Order>> _buyerOrders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Order>> buyerOrders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.models.Order> _currentTrackingOrder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.Order> currentTrackingOrder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    
    public OrdersViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Order>> getSellerOrders() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Order>> getBuyerOrders() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.Order> getCurrentTrackingOrder() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    public final void listenToSellerOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String sellerId) {
    }
    
    public final void listenToBuyerOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String buyerId) {
    }
    
    public final void startTracking(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
    }
    
    public final void updateStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.OrderStatus newStatus) {
    }
    
    public final void acceptOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
    }
    
    public final void rejectOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
    }
    
    public final void markPreparing(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
    }
    
    public final void markReady(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
    }
    
    public final void markOutForDelivery(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
    }
    
    public final void markDelivered(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
    }
}