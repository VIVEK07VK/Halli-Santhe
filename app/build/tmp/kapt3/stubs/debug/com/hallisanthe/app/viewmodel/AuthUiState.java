package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0006\t\n\u000b\f\r\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthUiState;", "", "()V", "Error", "Idle", "Loading", "PasswordResetSent", "Success", "Unauthenticated", "Lcom/hallisanthe/app/viewmodel/AuthUiState$Error;", "Lcom/hallisanthe/app/viewmodel/AuthUiState$Idle;", "Lcom/hallisanthe/app/viewmodel/AuthUiState$Loading;", "Lcom/hallisanthe/app/viewmodel/AuthUiState$PasswordResetSent;", "Lcom/hallisanthe/app/viewmodel/AuthUiState$Success;", "Lcom/hallisanthe/app/viewmodel/AuthUiState$Unauthenticated;", "app_debug"})
public abstract class AuthUiState {
    
    private AuthUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthUiState$Error;", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.hallisanthe.app.viewmodel.AuthUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.hallisanthe.app.viewmodel.AuthUiState.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthUiState$Idle;", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "()V", "app_debug"})
    public static final class Idle extends com.hallisanthe.app.viewmodel.AuthUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.hallisanthe.app.viewmodel.AuthUiState.Idle INSTANCE = null;
        
        private Idle() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthUiState$Loading;", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "()V", "app_debug"})
    public static final class Loading extends com.hallisanthe.app.viewmodel.AuthUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.hallisanthe.app.viewmodel.AuthUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthUiState$PasswordResetSent;", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "()V", "app_debug"})
    public static final class PasswordResetSent extends com.hallisanthe.app.viewmodel.AuthUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.hallisanthe.app.viewmodel.AuthUiState.PasswordResetSent INSTANCE = null;
        
        private PasswordResetSent() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthUiState$Success;", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "user", "Lcom/hallisanthe/app/models/UserModel;", "(Lcom/hallisanthe/app/models/UserModel;)V", "getUser", "()Lcom/hallisanthe/app/models/UserModel;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class Success extends com.hallisanthe.app.viewmodel.AuthUiState {
        @org.jetbrains.annotations.Nullable()
        private final com.hallisanthe.app.models.UserModel user = null;
        
        public Success(@org.jetbrains.annotations.Nullable()
        com.hallisanthe.app.models.UserModel user) {
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.hallisanthe.app.models.UserModel getUser() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.hallisanthe.app.models.UserModel component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.hallisanthe.app.viewmodel.AuthUiState.Success copy(@org.jetbrains.annotations.Nullable()
        com.hallisanthe.app.models.UserModel user) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthUiState$Unauthenticated;", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "()V", "app_debug"})
    public static final class Unauthenticated extends com.hallisanthe.app.viewmodel.AuthUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.hallisanthe.app.viewmodel.AuthUiState.Unauthenticated INSTANCE = null;
        
        private Unauthenticated() {
        }
    }
}