package com.hallisanthe.app.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0006\u0010\f\u001a\u00020\rJ&\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0012\u0010\u0016\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002JJ\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00142\b\b\u0002\u0010\u001d\u001a\u00020\u00112\b\b\u0002\u0010\u001e\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010!J\u0006\u0010\"\u001a\u00020#R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006%"}, d2 = {"Lcom/hallisanthe/app/repository/AuthRepository;", "", "()V", "authManager", "Lcom/hallisanthe/app/firebase/FirebaseAuthManager;", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "getCurrentUser", "()Lcom/google/firebase/auth/FirebaseUser;", "getSessionUser", "Lcom/hallisanthe/app/models/UserModel;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isLoggedIn", "", "loginWithEmail", "Lcom/hallisanthe/app/repository/AuthResult;", "email", "", "password", "expectedRole", "Lcom/hallisanthe/app/models/UserRole;", "(Ljava/lang/String;Ljava/lang/String;Lcom/hallisanthe/app/models/UserRole;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mapFirebaseError", "e", "", "registerUser", "fullName", "phone", "role", "shopName", "villageName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/hallisanthe/app/models/UserRole;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendPasswordReset", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signOut", "", "Companion", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.firebase.FirebaseAuthManager authManager = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.hallisanthe.app.repository.AuthRepository.Companion Companion = null;
    
    public AuthRepository() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return null;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final void signOut() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loginWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.UserRole expectedRole, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hallisanthe.app.repository.AuthResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object registerUser(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.UserRole role, @org.jetbrains.annotations.NotNull()
    java.lang.String shopName, @org.jetbrains.annotations.NotNull()
    java.lang.String villageName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hallisanthe.app.repository.AuthResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSessionUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hallisanthe.app.models.UserModel> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendPasswordReset(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.hallisanthe.app.repository.AuthResult> $completion) {
        return null;
    }
    
    private final java.lang.String mapFirebaseError(java.lang.Throwable e) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/hallisanthe/app/repository/AuthRepository$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}