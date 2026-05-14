package com.hallisanthe.app.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.hallisanthe.app.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseAuthManager {

    private val auth: FirebaseAuth? 
        get() = try { FirebaseAuth.getInstance() } catch (e: Exception) { null }
    private val firestore: FirebaseFirestore? 
        get() = try { FirebaseFirestore.getInstance() } catch (e: Exception) { null }

    val currentUser: FirebaseUser? get() = auth?.currentUser

    companion object {
        private const val TAG = "FirebaseAuthManager"
    }

    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "signInWithEmail: attempting login for $email")
            val firebaseAuth = auth ?: throw Exception("Auth service unavailable")
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Login failed: user is null"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "signInWithEmail: FAILED", e)
            Result.failure(e)
        }
    }

    suspend fun registerWithEmail(email: String, password: String): Result<FirebaseUser> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "registerWithEmail: attempting registration for $email")
            val firebaseAuth = auth ?: throw Exception("Auth service unavailable")
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Registration failed: user is null"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "registerWithEmail: FAILED", e)
            Result.failure(e)
        }
    }


    suspend fun saveUserToFirestore(user: UserModel): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val db = firestore ?: throw Exception("Firestore service unavailable")
            db.collection("users")
                .document(user.uid)
                .set(user.toMap())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "saveUserToFirestore: FAILED", e)
            Result.failure(e)
        }
    }

    suspend fun getUserFromFirestore(uid: String): Result<UserModel?> = withContext(Dispatchers.IO) {
        try {
            val db = firestore ?: throw Exception("Firestore service unavailable")
            // Force fetch from SERVER during login to ensure profile existence
            // If server is unreachable, it will throw an exception which we handle
            val doc = db.collection("users").document(uid).get(Source.SERVER).await()
            if (doc.exists()) {
                @Suppress("UNCHECKED_CAST")
                val model = UserModel.fromMap(doc.data as Map<String, Any?>)
                Result.success(model)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getUserFromFirestore: FAILED", e)
            Result.failure(e)
        }
    }

    suspend fun checkUserExists(uid: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val db = firestore ?: return@withContext false
            val doc = db.collection("users").document(uid).get().await()
            doc.exists()
        } catch (e: Exception) {
            false
        }
    }

    fun signOut() {
        auth?.signOut()
    }

    fun isLoggedIn(): Boolean = auth?.currentUser != null

    suspend fun sendPasswordReset(email: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val firebaseAuth = auth ?: throw Exception("Auth service unavailable")
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "sendPasswordReset: FAILED", e)
            Result.failure(e)
        }
    }
}
