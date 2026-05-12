package com.hallisanthe.app.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000^\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u00ae\u0001\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u008b\u0001\u0010\u0006\u001a\u0086\u0001\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\b\u00a2\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001aD\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a@\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u00032\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u001fH\u0007\u001a8\u0010 \u001a\u00020\u00012\b\b\u0002\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\b2\u0006\u0010%\u001a\u00020&2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020)0(H\u0007\u00a8\u0006*"}, d2 = {"AddEditProductDialog", "", "existingProduct", "Lcom/hallisanthe/app/models/Product;", "onDismiss", "Lkotlin/Function0;", "onSave", "Lkotlin/Function6;", "", "Lkotlin/ParameterName;", "name", "", "price", "", "stock", "category", "imageUrl", "unit", "SellerDashboardScreen", "sellerViewModel", "Lcom/hallisanthe/app/viewmodel/SellerViewModel;", "earningsViewModel", "Lcom/hallisanthe/app/viewmodel/EarningsViewModel;", "onNavigateToOrders", "onNavigateToAddProduct", "onLogout", "SellerProductCard", "product", "onEdit", "onDelete", "onUpdateStock", "Lkotlin/Function1;", "StatCard", "modifier", "Landroidx/compose/ui/Modifier;", "title", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "gradient", "", "Landroidx/compose/ui/graphics/Color;", "app_debug"})
public final class SellerDashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SellerDashboardScreen(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.SellerViewModel sellerViewModel, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.viewmodel.EarningsViewModel earningsViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToOrders, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAddProduct, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AddEditProductDialog(@org.jetbrains.annotations.Nullable()
    com.hallisanthe.app.models.Product existingProduct, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function6<? super java.lang.String, ? super java.lang.Double, ? super java.lang.Integer, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatCard(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.util.List<androidx.compose.ui.graphics.Color> gradient) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SellerProductCard(@org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onUpdateStock) {
    }
}