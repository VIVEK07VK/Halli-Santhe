package com.hallisanthe.app.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object FirebaseManager {
    val auth: com.google.firebase.auth.FirebaseAuth by lazy { com.google.firebase.auth.FirebaseAuth.getInstance() }
    val firestore: com.google.firebase.firestore.FirebaseFirestore by lazy { com.google.firebase.firestore.FirebaseFirestore.getInstance() }
    val storage: com.google.firebase.storage.FirebaseStorage? 
        get() = try { com.google.firebase.storage.FirebaseStorage.getInstance() } catch (e: Exception) { null }
}
