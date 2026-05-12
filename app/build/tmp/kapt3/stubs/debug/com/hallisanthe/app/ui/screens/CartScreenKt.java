package com.hallisanthe.app.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a:\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a,\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0010"}, d2 = {"CartBottomBar", "", "total", "", "onCheckout", "Lkotlin/Function0;", "CartItemRow", "item", "Lcom/hallisanthe/app/models/CartItem;", "onIncrease", "onDecrease", "onRemove", "CartScreen", "cartViewModel", "Lcom/hallisanthe/app/viewmodel/CartViewModel;", "onBack", "app_debug"})
public final class CartScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void CartScreen(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.CartViewModel cartViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCheckout) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CartItemRow(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.CartItem item, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onIncrease, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDecrease, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CartBottomBar(double total, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCheckout) {
    }
}