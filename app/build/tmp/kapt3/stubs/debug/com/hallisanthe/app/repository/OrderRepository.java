package com.hallisanthe.app.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u000b2\u0006\u0010\f\u001a\u00020\rJ\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000f0\u000b2\u0006\u0010\u0010\u001a\u00020\rJ\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000f0\u000b2\u0006\u0010\u0012\u001a\u00020\rJ\u001e\u0010\u0013\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/hallisanthe/app/repository/OrderRepository;", "", "()V", "ordersCollection", "Lcom/google/firebase/firestore/CollectionReference;", "createOrder", "", "order", "Lcom/hallisanthe/app/models/Order;", "(Lcom/hallisanthe/app/models/Order;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOrderTracking", "Lkotlinx/coroutines/flow/Flow;", "orderId", "", "getOrdersForBuyer", "", "buyerId", "getOrdersForSeller", "sellerId", "updateOrderStatus", "newStatus", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class OrderRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference ordersCollection = null;
    
    public OrderRepository() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.hallisanthe.app.models.Order>> getOrdersForSeller(@org.jetbrains.annotations.NotNull()
    java.lang.String sellerId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.hallisanthe.app.models.Order>> getOrdersForBuyer(@org.jetbrains.annotations.NotNull()
    java.lang.String buyerId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.hallisanthe.app.models.Order> getOrderTracking(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateOrderStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String newStatus, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createOrder(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Order order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}