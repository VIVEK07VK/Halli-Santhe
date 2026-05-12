package com.hallisanthe.app.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0016\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0017\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000eR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/hallisanthe/app/repository/ProductRepository;", "", "productDao", "Lcom/hallisanthe/app/room/ProductDao;", "(Lcom/hallisanthe/app/room/ProductDao;)V", "localProducts", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/hallisanthe/app/models/Product;", "getLocalProducts", "()Lkotlinx/coroutines/flow/Flow;", "addProduct", "", "product", "(Lcom/hallisanthe/app/models/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProduct", "productId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fetchProductsFromFirebase", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProductsBySellerId", "sellerId", "updateProduct", "app_debug"})
public final class ProductRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.room.ProductDao productDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.hallisanthe.app.models.Product>> localProducts = null;
    
    public ProductRepository(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.room.ProductDao productDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.hallisanthe.app.models.Product>> getLocalProducts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchProductsFromFirebase(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getProductsBySellerId(@org.jetbrains.annotations.NotNull()
    java.lang.String sellerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.hallisanthe.app.models.Product>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addProduct(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateProduct(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteProduct(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}