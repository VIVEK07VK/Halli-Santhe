package com.hallisanthe.app.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J:\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/hallisanthe/app/utils/UpiPaymentManager;", "", "()V", "KNOWN_UPI_PACKAGES", "", "", "createUpiIntent", "Landroid/content/Intent;", "upiId", "name", "transactionId", "note", "amount", "packageName", "getInstalledUpiApps", "", "Lcom/hallisanthe/app/utils/UpiApp;", "context", "Landroid/content/Context;", "isAppInstalled", "", "app_debug"})
public final class UpiPaymentManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> KNOWN_UPI_PACKAGES = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.hallisanthe.app.utils.UpiPaymentManager INSTANCE = null;
    
    private UpiPaymentManager() {
        super();
    }
    
    /**
     * Dynamically fetches all apps installed on the device that can handle UPI intents.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.hallisanthe.app.utils.UpiApp> getInstalledUpiApps(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Creates a UPI payment intent with the standard upi://pay format.
     */
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent createUpiIntent(@org.jetbrains.annotations.NotNull()
    java.lang.String upiId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String transactionId, @org.jetbrains.annotations.NotNull()
    java.lang.String note, @org.jetbrains.annotations.NotNull()
    java.lang.String amount, @org.jetbrains.annotations.Nullable()
    java.lang.String packageName) {
        return null;
    }
    
    /**
     * Legacy check for a specific app by package name.
     * Note: This is less reliable than queryIntentActivities on Android 11+ without proper <queries>.
     */
    public final boolean isAppInstalled(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
        return false;
    }
}