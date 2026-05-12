package com.hallisanthe.app.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0018R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/hallisanthe/app/repository/CartRepository;", "", "cartDao", "Lcom/hallisanthe/app/room/CartDao;", "(Lcom/hallisanthe/app/room/CartDao;)V", "allCartItems", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/hallisanthe/app/models/CartItem;", "getAllCartItems", "()Lkotlinx/coroutines/flow/Flow;", "addOrUpdateItem", "", "cartItem", "(Lcom/hallisanthe/app/models/CartItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearCart", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeItem", "productId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateQuantity", "newQuantity", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CartRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.room.CartDao cartDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.hallisanthe.app.models.CartItem>> allCartItems = null;
    
    public CartRepository(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.room.CartDao cartDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.hallisanthe.app.models.CartItem>> getAllCartItems() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addOrUpdateItem(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.CartItem cartItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateQuantity(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, int newQuantity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeItem(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearCart(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}