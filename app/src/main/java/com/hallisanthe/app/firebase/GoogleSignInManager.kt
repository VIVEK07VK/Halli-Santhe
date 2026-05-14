package com.hallisanthe.app.firebase

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.hallisanthe.app.R

class GoogleSignInManager(private val context: Context) {

    private val gso: GoogleSignInOptions by lazy {
        val webClientId = context.getString(R.string.default_web_client_id)
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .requestProfile()
            .build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(context, gso)
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    suspend fun signOut() {
        try {
            googleSignInClient.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
