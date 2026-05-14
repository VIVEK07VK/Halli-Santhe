package com.hallisanthe.app.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.hallisanthe.app.firebase.FirebaseAuthManager
import com.hallisanthe.app.models.UserModel
import com.hallisanthe.app.models.UserRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {

    private val authManager = FirebaseAuthManager()

    companion object {
        private const val TAG = "AuthRepository"
    }

    val currentUser: FirebaseUser? get() = authManager.currentUser
    fun isLoggedIn() = authManager.isLoggedIn()
    fun signOut() = authManager.signOut()

    suspend fun loginWithEmail(email: String, password: String, expectedRole: UserRole): AuthResult = withContext(Dispatchers.IO) {
        val result = authManager.signInWithEmail(email, password)
        if (result.isSuccess) {
            val user = result.getOrNull()!!
            
            // Try fetching profile with a retry mechanism
            var userModel: com.hallisanthe.app.models.UserModel? = null
            var lastError: Throwable? = null
            
            repeat(3) { attempt ->
                val profileResult = authManager.getUserFromFirestore(user.uid)
                if (profileResult.isSuccess) {
                    userModel = profileResult.getOrNull()
                    if (userModel != null) return@repeat // Success!
                } else {
                    lastError = profileResult.exceptionOrNull()
                }
                if (attempt < 2) kotlinx.coroutines.delay(1000) // Wait before retry
            }
            
            if (userModel == null) {
                if (lastError == null) {
                    authManager.signOut()
                    return@withContext AuthResult.Error("Account profile not found. If you just registered, please wait a moment and try again.")
                } else {
                    return@withContext AuthResult.Error("Connection error while fetching profile: ${lastError?.localizedMessage}. Please try again.")
                }
            }

            if (!userModel!!.role.equals(expectedRole.name, ignoreCase = true)) {
                authManager.signOut()
                val actualRole = userModel!!.role.lowercase().replaceFirstChar { it.uppercase() }
                return@withContext AuthResult.Error("This account is registered as $actualRole. Please use the correct login side.")
            }
            AuthResult.Success(userModel!!)
        } else {
            AuthResult.Error(mapFirebaseError(result.exceptionOrNull()))
        }
    }

    suspend fun registerUser(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        role: UserRole,
        shopName: String = "",
        villageName: String = ""
    ): AuthResult = withContext(Dispatchers.IO + kotlinx.coroutines.NonCancellable) {
        try {
            // STEP 1: Create Firebase Auth Account
            val registerResult = authManager.registerWithEmail(email, password)
            if (registerResult.isFailure) {
                return@withContext AuthResult.Error(mapFirebaseError(registerResult.exceptionOrNull()))
            }

            val firebaseUser = registerResult.getOrNull()!!
            val model = UserModel(
                uid         = firebaseUser.uid,
                fullName    = fullName,
                email       = email,
                phone       = phone,
                role        = role.name,
                shopName    = shopName,
                villageName = villageName
            )

            // STEP 2: Save to Firestore with a robust strategy
            val saveResult = try {
                // Increase timeout to 30s for village/slow networks
                kotlinx.coroutines.withTimeout(30000) {
                    authManager.saveUserToFirestore(model)
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "registerUser: Profile save FAILED", e)
                Result.failure(e)
            }

            if (saveResult.isSuccess) {
                AuthResult.Success(model)
            } else {
                // If profile save fails, we MUST report it so the user can try again
                // and we don't end up with "ghost" accounts.
                AuthResult.Error("Account created but profile save failed. Please check internet and try again.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "registerUser: FATAL ERROR", e)
            AuthResult.Error("Registration error: ${e.localizedMessage}")
        }
    }


    suspend fun getSessionUser(): UserModel? = withContext(Dispatchers.IO) {
        val uid = authManager.currentUser?.uid ?: return@withContext null
        authManager.getUserFromFirestore(uid).getOrNull()
    }

    suspend fun sendPasswordReset(email: String): AuthResult = withContext(Dispatchers.IO) {
        val result = authManager.sendPasswordReset(email)
        if (result.isSuccess) {
            AuthResult.Success(null)
        } else {
            AuthResult.Error(mapFirebaseError(result.exceptionOrNull()))
        }
    }

    private fun mapFirebaseError(e: Throwable?): String {
        if (e == null) return "Authentication failed"
        
        // Handle Firebase Auth Errors
        if (e is com.google.firebase.auth.FirebaseAuthException) {
            return when (e.errorCode) {
                "ERROR_EMAIL_ALREADY_IN_USE" -> "This email is already registered. Try logging in."
                "ERROR_INVALID_EMAIL" -> "Please enter a valid email address."
                "ERROR_WEAK_PASSWORD" -> "Password is too weak. Use at least 6 characters."
                "ERROR_WRONG_PASSWORD" -> "Incorrect password. Please try again."
                "ERROR_USER_NOT_FOUND" -> "No account found with this email."
                "ERROR_NETWORK_REQUEST_FAILED" -> "Network error. Please check your internet."
                "ERROR_TOO_MANY_REQUESTS" -> "Too many attempts. Please try again later."
                else -> e.message ?: "Authentication failed"
            }
        }

        // Handle Firestore Errors
        if (e is com.google.firebase.firestore.FirebaseFirestoreException) {
            return when (e.code) {
                com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED -> "Permission denied. Please contact support."
                com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAVAILABLE -> "Database is currently unavailable. Try later."
                else -> "Database error: ${e.message}"
            }
        }

        return e.message ?: "An unexpected error occurred"
    }
}

sealed class AuthResult {
    data class Success(val user: UserModel?) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
