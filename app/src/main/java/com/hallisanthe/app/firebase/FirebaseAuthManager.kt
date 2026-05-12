package com.hallisanthe.app.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hallisanthe.app.models.UserModel
import kotlinx.coroutines.tasks.await

class FirebaseAuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    val currentUser: FirebaseUser? get() = auth.currentUser

    companion object {
        private const val TAG = "FirebaseAuthManager"
    }

    // ─── Email / Password ────────────────────────────────────────────

    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            Log.d(TAG, "signInWithEmail: attempting login for $email")
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Log.d(TAG, "signInWithEmail: success uid=${user.uid}")
                Result.success(user)
            } else {
                Log.e(TAG, "signInWithEmail: result.user is null after success")
                Result.failure(Exception("Login failed: user is null"))
            }
        } catch (e: FirebaseAuthException) {
            Log.e(TAG, "signInWithEmail: FirebaseAuthException errorCode=${e.errorCode} msg=${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "signInWithEmail: Exception ${e.javaClass.simpleName} msg=${e.message}")
            Result.failure(e)
        }
    }

    suspend fun registerWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            Log.d(TAG, "registerWithEmail: attempting registration for $email")
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Log.d(TAG, "registerWithEmail: success uid=${user.uid}")
                Result.success(user)
            } else {
                Log.e(TAG, "registerWithEmail: result.user is null after success")
                Result.failure(Exception("Registration failed: user is null"))
            }
        } catch (e: FirebaseAuthException) {
            // ⚠️ CRITICAL FIX: Use e.errorCode (e.g. "ERROR_EMAIL_ALREADY_IN_USE"),
            // NOT e.message which is a long verbose string that never matches REST codes.
            Log.e(TAG, "registerWithEmail: FirebaseAuthException errorCode=${e.errorCode} msg=${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "registerWithEmail: Exception ${e.javaClass.simpleName} msg=${e.message}")
            Result.failure(e)
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            Log.d(TAG, "sendPasswordResetEmail: sending to $email")
            auth.sendPasswordResetEmail(email).await()
            Log.d(TAG, "sendPasswordResetEmail: sent successfully")
            Result.success(Unit)
        } catch (e: FirebaseAuthException) {
            Log.e(TAG, "sendPasswordResetEmail: FirebaseAuthException errorCode=${e.errorCode} msg=${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "sendPasswordResetEmail: Exception ${e.javaClass.simpleName} msg=${e.message}")
            Result.failure(e)
        }
    }

    // ─── Google Sign-In ──────────────────────────────────────────────

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            Log.d(TAG, "signInWithGoogle: attempting Google credential sign-in")
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user
            if (user != null) {
                Log.d(TAG, "signInWithGoogle: success uid=${user.uid}")
                Result.success(user)
            } else {
                Log.e(TAG, "signInWithGoogle: result.user is null")
                Result.failure(Exception("Google sign-in failed: user is null"))
            }
        } catch (e: FirebaseAuthException) {
            Log.e(TAG, "signInWithGoogle: FirebaseAuthException errorCode=${e.errorCode} msg=${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "signInWithGoogle: Exception ${e.javaClass.simpleName} msg=${e.message}")
            Result.failure(e)
        }
    }

    // ─── Firestore ───────────────────────────────────────────────────

    suspend fun saveUserToFirestore(user: UserModel): Result<Unit> {
        return try {
            Log.d(TAG, "saveUserToFirestore: saving uid=${user.uid} role=${user.role}")
            firestore.collection("users")
                .document(user.uid)
                .set(user.toMap())
                .await()
            Log.d(TAG, "saveUserToFirestore: saved successfully")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "saveUserToFirestore: FAILED uid=${user.uid} error=${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun getUserFromFirestore(uid: String): Result<UserModel?> {
        return try {
            Log.d(TAG, "getUserFromFirestore: fetching uid=$uid")
            val doc = firestore.collection("users").document(uid).get().await()
            if (doc.exists()) {
                @Suppress("UNCHECKED_CAST")
                val model = UserModel.fromMap(doc.data as Map<String, Any?>)
                Log.d(TAG, "getUserFromFirestore: found user role=${model.role}")
                Result.success(model)
            } else {
                Log.w(TAG, "getUserFromFirestore: no document found for uid=$uid")
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getUserFromFirestore: FAILED uid=$uid error=${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun checkUserExists(uid: String): Boolean {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.exists().also { Log.d(TAG, "checkUserExists: uid=$uid exists=$it") }
        } catch (e: Exception) {
            Log.e(TAG, "checkUserExists: FAILED uid=$uid error=${e.message}", e)
            false
        }
    }

    // ─── Session ─────────────────────────────────────────────────────

    fun signOut() {
        Log.d(TAG, "signOut: signing out uid=${auth.currentUser?.uid}")
        auth.signOut()
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null
}
