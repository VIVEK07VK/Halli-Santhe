package com.hallisanthe.app.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 (2\u00020\u0001:\u0001(B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u001e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u0015JJ\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010$\u001a\u00020\u001b2\b\b\u0002\u0010%\u001a\u00020\u001bJ\u0006\u0010&\u001a\u00020\u0015J\u000e\u0010\'\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bR\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011\u00a8\u0006)"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_sessionUser", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/hallisanthe/app/models/UserModel;", "_uiState", "Lcom/hallisanthe/app/viewmodel/AuthUiState;", "repository", "Lcom/hallisanthe/app/repository/AuthRepository;", "sessionManager", "Lcom/hallisanthe/app/utils/SessionManager;", "sessionUser", "Lkotlinx/coroutines/flow/StateFlow;", "getSessionUser", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "checkSession", "", "handleAuthResult", "result", "Lcom/hallisanthe/app/repository/AuthResult;", "loginWithEmail", "email", "", "password", "role", "Lcom/hallisanthe/app/models/UserRole;", "logout", "register", "fullName", "phone", "confirmPassword", "shopName", "villageName", "resetState", "sendPasswordReset", "Companion", "app_debug"})
public final class AuthViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.repository.AuthRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.hallisanthe.app.utils.SessionManager sessionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.viewmodel.AuthUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.AuthUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.hallisanthe.app.models.UserModel> _sessionUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserModel> sessionUser = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthViewModel";
    @org.jetbrains.annotations.NotNull()
    public static final com.hallisanthe.app.viewmodel.AuthViewModel.Companion Companion = null;
    
    public AuthViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.viewmodel.AuthUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.hallisanthe.app.models.UserModel> getSessionUser() {
        return null;
    }
    
    /**
     * Robust session check to prevent second-launch crashes.
     * Verifies both Firebase Auth and Firestore data availability.
     */
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
    
    private final void handleAuthResult(com.hallisanthe.app.repository.AuthResult result) {
    }
    
    public final void logout() {
    }
    
    public final void sendPasswordReset(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void resetState() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/hallisanthe/app/viewmodel/AuthViewModel$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}