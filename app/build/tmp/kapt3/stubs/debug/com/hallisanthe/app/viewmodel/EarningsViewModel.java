package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/hallisanthe/app/viewmodel/EarningsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_earningsSummary", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/hallisanthe/app/viewmodel/SellerEarningsSummary;", "currentUserId", "", "getCurrentUserId", "()Ljava/lang/String;", "earningsSummary", "Lkotlinx/coroutines/flow/StateFlow;", "getEarningsSummary", "()Lkotlinx/coroutines/flow/StateFlow;", "repository", "Lcom/hallisanthe/app/repository/EarningsRepository;", "loadEarnings", "", "app_debug"})
public final class EarningsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.EarningsRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.viewmodel.SellerEarningsSummary> _earningsSummary = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.SellerEarningsSummary> earningsSummary = null;
    
    public EarningsViewModel() {
        super();
    }
    
    private final java.lang.String getCurrentUserId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.SellerEarningsSummary> getEarningsSummary() {
        return null;
    }
    
    private final void loadEarnings() {
    }
}