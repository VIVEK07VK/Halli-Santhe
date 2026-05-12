package com.hallisanthe.app.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.hallisanthe.app.firebase.FirebaseAuthManager
import com.hallisanthe.app.models.UserModel
import com.hallisanthe.app.models.UserRole

class AuthRepository {

    private val authManager = FirebaseAuthManager()

    companion object {
        private const val TAG = "AuthRepository"
    }

    val currentUser: FirebaseUser? get() = authManager.currentUser
    fun isLoggedIn() = authManager.isLoggedIn()
    fun signOut() = authManager.signOut()

    // ─── Login ───────────────────────────────────────────────────────

    suspend fun loginWithEmail(email: String, password: String, expectedRole: UserRole): AuthResult {
        Log.d(TAG, "loginWithEmail: email=$email expectedRole=${expectedRole.name}")
        val result = authManager.signInWithEmail(email, password)
        return if (result.isSuccess) {
            val user = result.getOrNull()!!
            Log.d(TAG, "loginWithEmail: Firebase auth OK, fetching Firestore profile uid=${user.uid}")
            val userModel = authManager.getUserFromFirestore(user.uid).getOrNull()
            Log.d(TAG, "loginWithEmail: Firestore profile fetched role=${userModel?.role}")
            
            if (userModel != null && !userModel.role.equals(expectedRole.name, ignoreCase = true)) {
                Log.e(TAG, "loginWithEmail: Role mismatch! Expected ${expectedRole.name}, got ${userModel.role}")
                authManager.signOut()
                val actualRole = userModel.role.lowercase().replaceFirstChar { it.uppercase() }
                return AuthResult.Error("This account is registered as $actualRole")
            }
            
            AuthResult.Success(userModel)
        } else {
            val error = mapFirebaseError(result.exceptionOrNull())
            Log.e(TAG, "loginWithEmail: FAILED mapped='$error' raw=${result.exceptionOrNull()?.message}")
            AuthResult.Error(error)
        }
    }

    // ─── Register ────────────────────────────────────────────────────

    suspend fun registerUser(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        role: UserRole,
        shopName: String = "",
        villageName: String = ""
    ): AuthResult {
        Log.d(TAG, "registerUser: starting registration email=$email role=${role.name}")

        // Step 1: Create Firebase Auth account
        val registerResult = authManager.registerWithEmail(email, password)
        if (registerResult.isFailure) {
            val error = mapFirebaseError(registerResult.exceptionOrNull())
            Log.e(TAG, "registerUser: Auth creation FAILED mapped='$error' raw=${registerResult.exceptionOrNull()?.message}")
            return AuthResult.Error(error)
        }

        val firebaseUser = registerResult.getOrNull()!!
        Log.d(TAG, "registerUser: Auth created uid=${firebaseUser.uid}, now saving to Firestore")

        // Step 2: Build user model with all required fields
        val model = UserModel(
            uid         = firebaseUser.uid,
            fullName    = fullName,
            email       = email,
            phone       = phone,
            role        = role.name,
            shopName    = shopName,
            villageName = villageName
        )

        // Step 3: Save to Firestore
        val saveResult = authManager.saveUserToFirestore(model)
        return if (saveResult.isSuccess) {
            Log.d(TAG, "registerUser: SUCCESS uid=${firebaseUser.uid} role=${role.name}")
            AuthResult.Success(model)
        } else {
            val error = mapFirebaseError(saveResult.exceptionOrNull())
            Log.e(TAG, "registerUser: Firestore save FAILED mapped='$error' raw=${saveResult.exceptionOrNull()?.message}")
            // Auth account was created but Firestore save failed — user can still be logged in
            // Return partial success with an in-memory model so app can proceed
            AuthResult.Error("Account created but profile save failed: $error")
        }
    }

    // ─── Google Sign-In ──────────────────────────────────────────────

    suspend fun loginWithGoogle(
        idToken: String,
        fullName: String = "",
        email: String = "",
        role: UserRole = UserRole.BUYER
    ): AuthResult {
        Log.d(TAG, "loginWithGoogle: attempting Google sign-in")
        val result = authManager.signInWithGoogle(idToken)
        if (result.isFailure) {
            val error = mapFirebaseError(result.exceptionOrNull())
            Log.e(TAG, "loginWithGoogle: FAILED mapped='$error'")
            return AuthResult.Error(error)
        }
        val firebaseUser = result.getOrNull()!!
        Log.d(TAG, "loginWithGoogle: Auth OK uid=${firebaseUser.uid}")

        val exists = authManager.checkUserExists(firebaseUser.uid)
        val model = if (exists) {
            Log.d(TAG, "loginWithGoogle: existing user, loading Firestore profile")
            val existingModel = authManager.getUserFromFirestore(firebaseUser.uid).getOrNull()
            if (existingModel != null && !existingModel.role.equals(role.name, ignoreCase = true)) {
                Log.e(TAG, "loginWithGoogle: Role mismatch! Expected ${role.name}, got ${existingModel.role}")
                authManager.signOut()
                val actualRole = existingModel.role.lowercase().replaceFirstChar { it.uppercase() }
                return AuthResult.Error("This account is registered as $actualRole")
            }
            existingModel
        } else {
            Log.d(TAG, "loginWithGoogle: new user, creating Firestore profile role=${role.name}")
            val newModel = UserModel(
                uid      = firebaseUser.uid,
                fullName = firebaseUser.displayName ?: fullName,
                email    = firebaseUser.email ?: email,
                phone    = "",
                role     = role.name
            )
            authManager.saveUserToFirestore(newModel)
            newModel
        }
        Log.d(TAG, "loginWithGoogle: SUCCESS role=${model?.role}")
        return AuthResult.Success(model)
    }

    // ─── Forgot Password ─────────────────────────────────────────────

    suspend fun sendPasswordReset(email: String): AuthResult {
        Log.d(TAG, "sendPasswordReset: email=$email")
        val result = authManager.sendPasswordResetEmail(email)
        return if (result.isSuccess) {
            Log.d(TAG, "sendPasswordReset: email sent")
            AuthResult.Success(null)
        } else {
            val error = mapFirebaseError(result.exceptionOrNull())
            Log.e(TAG, "sendPasswordReset: FAILED mapped='$error'")
            AuthResult.Error(error)
        }
    }

    // ─── Session ─────────────────────────────────────────────────────

    suspend fun getSessionUser(): UserModel? {
        val uid = authManager.currentUser?.uid ?: return null
        Log.d(TAG, "getSessionUser: uid=$uid")
        return authManager.getUserFromFirestore(uid).getOrNull()
    }

    // ─── Error Mapping ────────────────────────────────────────────────
    // ⚠️ CRITICAL FIX: Firebase Android SDK throws FirebaseAuthException.
    // The correct way to identify the error is via e.errorCode (not e.message).
    // Error codes: https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuthException

    private fun mapFirebaseError(e: Throwable?): String {
        if (e == null) return "An unexpected error occurred"

        // ── FirebaseAuthException: check errorCode first (most reliable) ──
        if (e is FirebaseAuthException) {
            val code = e.errorCode
            Log.d(TAG, "mapFirebaseError: FirebaseAuthException errorCode=$code")
            return when (code) {
                // Registration errors
                "ERROR_EMAIL_ALREADY_IN_USE"       -> "This email is already registered. Please sign in instead."
                "ERROR_INVALID_EMAIL"              -> "Invalid email format. Please enter a valid email address."
                "ERROR_WEAK_PASSWORD"              -> "Password is too weak. Use at least 6 characters."
                "ERROR_OPERATION_NOT_ALLOWED"      -> "Email/password sign-in is not enabled. Contact support."

                // Login errors
                "ERROR_WRONG_PASSWORD"             -> "Incorrect password. Please try again."
                "ERROR_USER_NOT_FOUND"             -> "No account found with this email. Please register."
                "ERROR_USER_DISABLED"              -> "This account has been disabled. Contact support."
                "ERROR_INVALID_CREDENTIAL"         -> "Incorrect email or password. Please try again."
                "ERROR_TOO_MANY_REQUESTS"          -> "Too many failed attempts. Please wait and try again."
                "ERROR_NETWORK_REQUEST_FAILED"     -> "No internet connection. Check your network and retry."
                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" ->
                    "An account already exists with this email using a different sign-in method."

                // Reset password
                "ERROR_MISSING_EMAIL"              -> "Please enter your email address."

                // Catch-all for any unhandled Firebase error codes
                else -> {
                    Log.w(TAG, "mapFirebaseError: unhandled errorCode=$code msg=${e.message}")
                    "Authentication error ($code). Please try again."
                }
            }
        }

        // ── Non-FirebaseAuthException: check message as fallback ──
        val msg = e.message ?: ""
        Log.d(TAG, "mapFirebaseError: non-Firebase exception msg=$msg")
        return when {
            msg.contains("INVALID_EMAIL", ignoreCase = true)           -> "Invalid email format."
            msg.contains("EMAIL_EXISTS", ignoreCase = true)            -> "This email is already registered."
            msg.contains("email address is already in use", ignoreCase = true) -> "This email is already registered."
            msg.contains("WEAK_PASSWORD", ignoreCase = true)           -> "Password is too weak. Use at least 6 characters."
            msg.contains("least 6 characters", ignoreCase = true)      -> "Password must be at least 6 characters."
            msg.contains("TOO_MANY_REQUESTS", ignoreCase = true)       -> "Too many attempts. Please try again later."
            msg.contains("NETWORK_ERROR", ignoreCase = true)           -> "No internet connection. Check your network."
            msg.contains("network", ignoreCase = true)                 -> "Network error. Please check your connection."
            msg.contains("badly formatted", ignoreCase = true)         -> "Invalid email format."
            msg.contains("no user record", ignoreCase = true)          -> "No account found with this email."
            msg.contains("password is invalid", ignoreCase = true)     -> "Incorrect password."
            else -> "Authentication failed: ${msg.take(80)}"
        }
    }
}

sealed class AuthResult {
    data class Success(val user: UserModel?) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
