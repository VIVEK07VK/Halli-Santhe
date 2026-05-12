package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ\u0018\u0010\u001b\u001a\u00020\u00142\b\u0010\u001c\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001d\u001a\u00020\u0011J\u001e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0011J\u0006\u0010!\u001a\u00020\u0014JJ\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00112\b\b\u0002\u0010&\u001a\u00020\u001a2\b\b\u0002\u0010\'\u001a\u00020\u001aJ\u0006\u0010(\u001a\u00020\u0014J\u000e\u0010)\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u001aJ2\u0010*\u001a\u0004\u0018\u00010\u001a2\u0006\u0010#\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u001aH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\r\u00a8\u0006,"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_sessionUser", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/hallisanthe/app/models/UserModel;", "_uiState", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "repository", "Lcom/hallisanthe/app/repository/AuthRepository;", "sessionUser", "Lkotlinx/coroutines/flow/StateFlow;", "getSessionUser", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "userRole", "Lcom/hallisanthe/app/models/UserRole;", "getUserRole", "checkSession", "", "getGoogleSignInIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "webClientId", "", "handleGoogleSignInResult", "data", "role", "loginWithEmail", "email", "password", "logout", "register", "fullName", "phone", "confirmPassword", "shopName", "villageName", "resetState", "sendPasswordReset", "validateRegistration", "Companion", "app_debug"})
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.AuthRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.viewmodel.AuthUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.AuthUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.models.UserModel> _sessionUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserModel> sessionUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserRole> userRole = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthViewModel";
    @org.jetbrains.annotations.NotNull()
    public static final com.hallisanthe.app.viewmodel.AuthViewModel.Companion Companion = null;
    
    public AuthViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.AuthUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserModel> getSessionUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserRole> getUserRole() {
        return null;
    }
    
    public final void checkSession() {
    }
    
    public final void loginWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.UserRole role) {
    }
    
    public final void register(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String confirmPassword, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.UserRole role, @org.jetbrains.annotations.NotNull()
    java.lang.String shopName, @org.jetbrains.annotations.NotNull()
    java.lang.String villageName) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent getGoogleSignInIntent(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String webClientId) {
        return null;
    }
    
    public final void handleGoogleSignInResult(@org.jetbrains.annotations.Nullable()
    android.content.Intent data, @org.jetbrains.annotations.NotNull()
    com.hallisanthe.app.models.UserRole role) {
    }
    
    public final void sendPasswordReset(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void logout() {
    }
    
    public final void resetState() {
    }
    
    private final java.lang.String validateRegistration(java.lang.String fullName, java.lang.String email, java.lang.String phone, java.lang.String password, java.lang.String confirmPassword) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthViewModel$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}