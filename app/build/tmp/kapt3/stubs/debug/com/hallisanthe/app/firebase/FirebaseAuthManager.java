package com.hallisanthe.app.firebase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u0000 (2\u00020\u0001:\u0001(B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J&\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0013J\u0006\u0010\u0018\u001a\u00020\u0010J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u00152\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJ$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00152\u0006\u0010 \u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J$\u0010#\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00152\u0006\u0010\u001a\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b$\u0010\u0013J,\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0\u00152\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\u001dJ\u0006\u0010\'\u001a\u00020\u001fR\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006)"}, d2 = {"Lcom/hallisanthe/app/firebase/FirebaseAuthManager;", "", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "getAuth", "()Lcom/google/firebase/auth/FirebaseAuth;", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "getCurrentUser", "()Lcom/google/firebase/auth/FirebaseUser;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "getFirestore", "()Lcom/google/firebase/firestore/FirebaseFirestore;", "checkUserExists", "", "uid", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserFromFirestore", "Lkotlin/Result;", "Lcom/hallisanthe/app/models/UserModel;", "getUserFromFirestore-gIAlu-s", "isLoggedIn", "registerWithEmail", "email", "password", "registerWithEmail-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserToFirestore", "", "user", "saveUserToFirestore-gIAlu-s", "(Lcom/hallisanthe/app/models/UserModel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendPasswordReset", "sendPasswordReset-gIAlu-s", "signInWithEmail", "signInWithEmail-0E7RQCE", "signOut", "Companion", "app_debug"})
public final class FirebaseAuthManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FirebaseAuthManager";
    @org.jetbrains.annotations.NotNull()
    public static final com.hallisanthe.app.firebase.FirebaseAuthManager.Companion Companion = null;
    
    public FirebaseAuthManager() {
        super();
    }
    
    private final com.google.firebase.auth.FirebaseAuth getAuth() {
        return null;
    }
    
    private final com.google.firebase.firestore.FirebaseFirestore getFirestore() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkUserExists(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    public final void signOut() {
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/hallisanthe/app/firebase/FirebaseAuthManager$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}