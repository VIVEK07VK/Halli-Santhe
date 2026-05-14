package com.hallisanthe.app.firebase;

/**
 * FirebaseStorageManager: Professional storage handling with robust error recovery.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J(\u0010\u0014\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0016"}, d2 = {"Lcom/hallisanthe/app/firebase/FirebaseStorageManager;", "", "()V", "TAG", "", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "getStorage", "()Lcom/google/firebase/storage/FirebaseStorage;", "storageRef", "Lcom/google/firebase/storage/StorageReference;", "getStorageRef", "()Lcom/google/firebase/storage/StorageReference;", "uploadProductImage", "context", "Landroid/content/Context;", "sellerId", "imageUri", "Landroid/net/Uri;", "(Landroid/content/Context;Ljava/lang/String;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadProfileImage", "userId", "app_debug"})
public final class FirebaseStorageManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FirebaseStorage";
    @org.jetbrains.annotations.NotNull()
    public static final com.hallisanthe.app.firebase.FirebaseStorageManager INSTANCE = null;
    
    private FirebaseStorageManager() {
        super();
    }
    
    private final com.google.firebase.storage.FirebaseStorage getStorage() {
        return null;
    }
    
    private final com.google.firebase.storage.StorageReference getStorageRef() {
        return null;
    }
    
    /**
     * Uploads a product image with a 100% success strategy:
     * 1. Copies URI to local temp file (avoids permission issues)
     * 2. Uses putFile with explicit Metadata
     * 3. Retries on transient failure
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uploadProductImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String sellerId, @org.jetbrains.annotations.NotNull()
    android.net.Uri imageUri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Standardized profile image upload with robust error handling.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uploadProfileImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    android.net.Uri imageUri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
}