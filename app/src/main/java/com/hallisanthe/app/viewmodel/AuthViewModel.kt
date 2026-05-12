package com.hallisanthe.app.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.hallisanthe.app.models.UserModel
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.repository.AuthRepository
import com.hallisanthe.app.repository.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ─── UI State ────────────────────────────────────────────────────────────────

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: UserModel?) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
    object PasswordResetSent : AuthUiState()
    object Unauthenticated : AuthUiState()
}

// ─── ViewModel ───────────────────────────────────────────────────────────────

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _sessionUser = MutableStateFlow<UserModel?>(null)
    val sessionUser: StateFlow<UserModel?> = _sessionUser.asStateFlow()

    val userRole: StateFlow<UserRole?> = _sessionUser.map { user ->
        user?.role?.let { 
            try { UserRole.valueOf(it) } catch (e: Exception) { null }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    companion object {
        private const val TAG = "AuthViewModel"
    }

    // ─── Session check on startup ─────────────────────────────────────

    fun checkSession() {
        viewModelScope.launch {
            Log.d(TAG, "checkSession: isLoggedIn=${repository.isLoggedIn()}")
            _uiState.value = AuthUiState.Loading
            if (repository.isLoggedIn()) {
                val user = repository.getSessionUser()
                Log.d(TAG, "checkSession: session user role=${user?.role}")
                _sessionUser.value = user
                _uiState.value = AuthUiState.Success(user)
            } else {
                Log.d(TAG, "checkSession: no active session")
                _uiState.value = AuthUiState.Unauthenticated
            }
        }
    }

    // ─── Email Login ──────────────────────────────────────────────────

    fun loginWithEmail(email: String, password: String, role: UserRole) {
        Log.d(TAG, "loginWithEmail: email=$email expectedRole=${role.name}")
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.loginWithEmail(email.trim(), password, role)
            _uiState.value = when (result) {
                is AuthResult.Success -> {
                    Log.d(TAG, "loginWithEmail: SUCCESS role=${result.user?.role}")
                    _sessionUser.value = result.user
                    AuthUiState.Success(result.user)
                }
                is AuthResult.Error -> {
                    Log.e(TAG, "loginWithEmail: ERROR '${result.message}'")
                    AuthUiState.Error(result.message)
                }
            }
        }
    }

    // ─── Register ─────────────────────────────────────────────────────

    fun register(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
        role: UserRole,
        shopName: String = "",
        villageName: String = ""
    ) {
        Log.d(TAG, "register: fullName=$fullName email=$email role=${role.name}")

        // ── Client-side validation ──
        val validationError = validateRegistration(fullName, email, phone, password, confirmPassword)
        if (validationError != null) {
            Log.w(TAG, "register: client validation failed: $validationError")
            _uiState.value = AuthUiState.Error(validationError)
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            Log.d(TAG, "register: calling repository.registerUser")
            val result = repository.registerUser(
                fullName    = fullName.trim(),
                email       = email.trim(),
                phone       = phone.trim(),
                password    = password,
                role        = role,
                shopName    = shopName.trim(),
                villageName = villageName.trim()
            )
            _uiState.value = when (result) {
                is AuthResult.Success -> {
                    Log.d(TAG, "register: SUCCESS uid=${result.user?.uid} role=${result.user?.role}")
                    _sessionUser.value = result.user
                    AuthUiState.Success(result.user)
                }
                is AuthResult.Error -> {
                    Log.e(TAG, "register: ERROR '${result.message}'")
                    AuthUiState.Error(result.message)
                }
            }
        }
    }

    // ─── Google Sign-In ───────────────────────────────────────────────

    fun getGoogleSignInIntent(context: Context, webClientId: String): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    fun handleGoogleSignInResult(data: Intent?, role: UserRole) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (idToken == null) {
                    Log.e(TAG, "handleGoogleSignInResult: idToken is null")
                    _uiState.value = AuthUiState.Error("Google sign-in failed. Please try again.")
                    return@launch
                }
                Log.d(TAG, "handleGoogleSignInResult: got idToken, calling repository")
                val result = repository.loginWithGoogle(
                    idToken  = idToken,
                    fullName = account.displayName ?: "",
                    email    = account.email ?: "",
                    role     = role
                )
                _uiState.value = when (result) {
                    is AuthResult.Success -> {
                        Log.d(TAG, "handleGoogleSignInResult: SUCCESS role=${result.user?.role}")
                        _sessionUser.value = result.user
                        AuthUiState.Success(result.user)
                    }
                    is AuthResult.Error -> {
                        Log.e(TAG, "handleGoogleSignInResult: ERROR '${result.message}'")
                        AuthUiState.Error(result.message)
                    }
                }
            } catch (e: ApiException) {
                Log.e(TAG, "handleGoogleSignInResult: ApiException statusCode=${e.statusCode} msg=${e.message}")
                _uiState.value = AuthUiState.Error("Google sign-in cancelled or failed (${e.statusCode})")
            }
        }
    }

    // ─── Forgot Password ──────────────────────────────────────────────

    fun sendPasswordReset(email: String) {
        if (email.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter your email address")
            return
        }
        Log.d(TAG, "sendPasswordReset: email=$email")
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.sendPasswordReset(email.trim())
            _uiState.value = when (result) {
                is AuthResult.Success -> {
                    Log.d(TAG, "sendPasswordReset: email sent")
                    AuthUiState.PasswordResetSent
                }
                is AuthResult.Error -> {
                    Log.e(TAG, "sendPasswordReset: ERROR '${result.message}'")
                    AuthUiState.Error(result.message)
                }
            }
        }
    }

    // ─── Logout ──────────────────────────────────────────────────────

    fun logout() {
        Log.d(TAG, "logout: signing out user uid=${_sessionUser.value?.uid}")
        repository.signOut()
        _sessionUser.value = null
        _uiState.value = AuthUiState.Unauthenticated
    }

    fun resetState() {
        if (_uiState.value !is AuthUiState.Loading) {
            _uiState.value = AuthUiState.Idle
        }
    }

    // ─── Validation ───────────────────────────────────────────────────

    private fun validateRegistration(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): String? {
        if (fullName.isBlank())
            return "Full name is required"
        if (fullName.trim().length < 2)
            return "Full name must be at least 2 characters"
        if (email.isBlank())
            return "Email address is required"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
            return "Invalid email format. Please enter a valid email address."
        if (phone.isBlank())
            return "Phone number is required"
        if (phone.trim().length < 10)
            return "Enter a valid 10-digit phone number"
        if (password.isBlank())
            return "Password is required"
        if (password.length < 6)
            return "Password must be at least 6 characters"
        if (password != confirmPassword)
            return "Passwords do not match"
        return null
    }
}
