package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0002R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/hallisanthe/app/viewmodel/ProductViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/hallisanthe/app/repository/ProductRepository;", "(Lcom/hallisanthe/app/repository/ProductRepository;)V", "products", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/hallisanthe/app/models/Product;", "getProducts", "()Lkotlinx/coroutines/flow/StateFlow;", "refreshProducts", "", "app_debug"})
public final class ProductViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Product>> products = null;
    
    public ProductViewModel(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.repository.ProductRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.hallisanthe.app.models.Product>> getProducts() {
        return null;
    }
    
    private final void refreshProducts() {
    }
}