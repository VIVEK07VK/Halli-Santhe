package com.hallisanthe.app.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

object FirebaseStorageManager {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    suspend fun uploadProductImage(imageUri: Uri): String? {
        return try {
            val fileName = "products/${UUID.randomUUID()}.jpg"
            val fileRef = storageRef.child(fileName)
            
            val uploadTask = fileRef.putFile(imageUri).await()
            val downloadUrl = fileRef.downloadUrl.await()
            
            downloadUrl.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
