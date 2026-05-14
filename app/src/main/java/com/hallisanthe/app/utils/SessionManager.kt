package com.hallisanthe.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.hallisanthe.app.models.UserRole

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("halli_santhe_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_USER_UID = "user_uid"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
    }

    /**
     * Safely saves user session data.
     */
    fun saveSession(uid: String, name: String, role: String, email: String = "") {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_UID, uid)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_ROLE, role)
            putString(KEY_USER_EMAIL, email)
            apply()
        }
    }

    /**
     * Validates if the current session is robust and not corrupted.
     */
    fun isSessionValid(): Boolean {
        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        val uid = prefs.getString(KEY_USER_UID, null)
        val role = prefs.getString(KEY_USER_ROLE, null)
        
        return isLoggedIn && !uid.isNullOrBlank() && !role.isNullOrBlank() && isValidRole(role)
    }

    private fun isValidRole(role: String): Boolean {
        return try {
            UserRole.valueOf(role)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getRole(): UserRole? {
        val role = prefs.getString(KEY_USER_ROLE, null) ?: return null
        return try { UserRole.valueOf(role) } catch (e: Exception) { null }
    }

    fun getUid(): String? = prefs.getString(KEY_USER_UID, null)
    
    fun getName(): String = prefs.getString(KEY_USER_NAME, "") ?: ""

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = isSessionValid()
}
