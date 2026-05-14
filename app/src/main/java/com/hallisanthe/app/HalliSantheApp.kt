package com.hallisanthe.app

import android.app.Application
import com.google.firebase.FirebaseApp

class HalliSantheApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            // Initialize Firebase once for the entire application
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            android.util.Log.e("HalliSantheApp", "Firebase initialization failed: ${e.message}", e)
        }
    }
}
