package com.hallisanthe.app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.UserModel
import com.hallisanthe.app.models.UserRole
import com.hallisanthe.app.repository.AuthRepository
import com.hallisanthe.app.repository.AuthResult
import com.hallisanthe.app.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: UserModel) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
    object Unauthenticated : AuthUiState()
    object PasswordResetSent : AuthUiState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository()
    private val sessionManager = SessionManager(application)

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _sessionUser = MutableStateFlow<UserModel?>(null)
    val sessionUser: StateFlow<UserModel?> = _sessionUser.asStateFlow()

    companion object {
        private const val TAG = "AuthViewModel"
    }

    /**
     * Robust session check to prevent second-launch crashes.
     * Verifies both Firebase Auth and Firestore data availability.
     */
    fun checkSession() {
        viewModelScope.launch {
            try {
                _uiState.value = AuthUiState.Loading
                
                if (repository.isLoggedIn()) {
                    val user = repository.getSessionUser()
                    
                    if (user != null && !user.role.isNullOrBlank()) {
                        // Success: Both Auth and Firestore data are present
                        _sessionUser.value = user
                        sessionManager.saveSession(user.uid, user.fullName, user.role, user.email)
                        _uiState.value = AuthUiState.Success(user)
                    } else {
                        // Partial data: Logged in but profile is missing or corrupted
                        Log.w(TAG, "checkSession: User logged in but profile missing or invalid. Clearing session.")
                        logout()
                    }
                } else {
                    // Not logged in
                    logout()
                }
            } catch (e: Exception) {
                Log.e(TAG, "checkSession: CRITICAL ERROR", e)
                logout() // Fallback to login screen on any error
            }
        }
    }

    fun loginWithEmail(email: String, password: String, role: UserRole) {
        viewModelScope.launch {
            try {
                _uiState.value = AuthUiState.Loading
                val result = repository.loginWithEmail(email.trim(), password, role)
                handleAuthResult(result)
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }

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
        if (fullName.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("All fields are required")
            return
        }
        if (password != confirmPassword) {
            _uiState.value = AuthUiState.Error("Passwords do not match")
            return
        }
        if (password.length < 6) {
            _uiState.value = AuthUiState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = AuthUiState.Loading
                Log.d(TAG, "register: starting registration for $email")
                val result = repository.registerUser(
                    fullName.trim(),
                    email.trim(),
                    phone.trim(),
                    password,
                    role,
                    shopName.trim(),
                    villageName.trim()
                )
                handleAuthResult(result)
            } catch (e: Exception) {
                Log.e(TAG, "register: FATAL EXCEPTION", e)
                _uiState.value = AuthUiState.Error(e.message ?: "Registration failed")
            }
        }
    }

    private fun handleAuthResult(result: AuthResult) {
        when (result) {
            is AuthResult.Success -> {
                val user = result.user
                if (user != null) {
                    _sessionUser.value = user
                    // Persist session before updating UI state
                    sessionManager.saveSession(user.uid, user.fullName, user.role, user.email)
                    _uiState.value = AuthUiState.Success(user)
                    Log.d(TAG, "handleAuthResult: SUCCESS for ${user.email}")
                } else {
                    _uiState.value = AuthUiState.Error("Auth succeeded but user data is empty.")
                }
            }
            is AuthResult.Error -> {
                Log.e(TAG, "handleAuthResult: ERROR - ${result.message}")
                _uiState.value = AuthUiState.Error(result.message)
            }
        }
    }

    fun logout() {
        try {
            repository.signOut()
            sessionManager.clearSession()
            _sessionUser.value = null
            _uiState.value = AuthUiState.Unauthenticated
        } catch (e: Exception) {
            Log.e(TAG, "logout error", e)
            _uiState.value = AuthUiState.Unauthenticated
        }
    }

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.sendPasswordReset(email)
            when (result) {
                is AuthResult.Success -> _uiState.value = AuthUiState.PasswordResetSent
                is AuthResult.Error -> _uiState.value = AuthUiState.Error(result.message)
            }
        }
    }

    fun resetState() {
        if (_uiState.value !is AuthUiState.Loading) {
            _uiState.value = AuthUiState.Idle
        }
    }
}
