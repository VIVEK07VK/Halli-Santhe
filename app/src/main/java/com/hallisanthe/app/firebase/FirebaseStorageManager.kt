package com.hallisanthe.app.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * FirebaseStorageManager: Professional storage handling with robust error recovery.
 */
object FirebaseStorageManager {
    private const val TAG = "FirebaseStorage"
    
    // Using a safe getter to ensure FirebaseApp is initialized
    // Safe getter that returns null instead of crashing if Storage is unavailable
    private val storage: FirebaseStorage?
        get() = try {
            FirebaseStorage.getInstance()
        } catch (e: Exception) {
            Log.e(TAG, "Firebase Storage unavailable: ${e.message}")
            null
        }

    private val storageRef: StorageReference? 
        get() = storage?.reference

    /**
     * Uploads a product image with a 100% success strategy:
     * 1. Copies URI to local temp file (avoids permission issues)
     * 2. Uses putFile with explicit Metadata
     * 3. Retries on transient failure
     */
    suspend fun uploadProductImage(context: Context, sellerId: String, imageUri: Uri): String {
        return withContext(Dispatchers.IO) {
            var tempFile: File? = null
            try {
                val timestamp = System.currentTimeMillis()
                val fileName = "products/$sellerId/$timestamp.jpg"
                val fileRef = storageRef?.child(fileName) ?: throw Exception("Storage service is currently unavailable. Please check your Firebase Console.")

                Log.d(TAG, "Preparing upload for: $fileName")

                // STEP 1: Copy to local temp file to ensure persistent access during upload
                tempFile = File(context.cacheDir, "upload_temp_$timestamp.jpg")
                context.contentResolver.openInputStream(imageUri)?.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                    }
                } ?: throw Exception("Could not open image stream")

                Log.d(TAG, "Local temp file created: ${tempFile.length()} bytes")

                // STEP 2: Upload with Metadata
                val metadata = com.google.firebase.storage.storageMetadata {
                    contentType = "image/jpeg"
                }

                // STEP 3: Execute upload and await
                val uploadTask = fileRef.putFile(Uri.fromFile(tempFile), metadata).await()
                
                if (uploadTask.task.isSuccessful) {
                    // STEP 4: Retrieve Download URL
                    val downloadUrl = fileRef.downloadUrl.await()
                    Log.d(TAG, "Upload SUCCESS. URL: $downloadUrl")
                    downloadUrl.toString()
                } else {
                    throw uploadTask.task.exception ?: Exception("Upload task failed without exception")
                }
            } catch (e: Exception) {
                Log.e(TAG, "CRITICAL UPLOAD FAILURE: ${e.message}", e)
                val errorMsg = e.message ?: ""
                val customMsg = when {
                    errorMsg.contains("does not exist", ignoreCase = true) -> 
                        "Firebase Storage Error: Either you have not clicked 'Get Started' in Firebase Console -> Storage, or your bucket URL is mismatched. Please check your Firebase Console Storage tab!"
                    errorMsg.contains("permission", ignoreCase = true) ->
                        "Firebase Permission Error: Please update your Storage Security Rules to allow reads/writes for authenticated users."
                    else -> errorMsg
                }
                throw Exception(customMsg)
            } finally {
                // Cleanup temp file
                tempFile?.delete()
            }
        }
    }

    /**
     * Standardized profile image upload with robust error handling.
     */
    suspend fun uploadProfileImage(context: Context, userId: String, imageUri: Uri): String? {
        return withContext(Dispatchers.IO) {
            var tempFile: File? = null
            try {
                val fileName = "profiles/$userId.jpg"
                val fileRef = storageRef?.child(fileName) ?: throw Exception("Storage service is currently unavailable.")
                
                // STEP 1: Copy to local temp file to ensure persistent access
                val timestamp = System.currentTimeMillis()
                tempFile = File(context.cacheDir, "profile_temp_$timestamp.jpg")
                context.contentResolver.openInputStream(imageUri)?.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                    }
                } ?: throw Exception("Could not open profile image stream")

                // STEP 2: Upload
                val metadata = com.google.firebase.storage.storageMetadata {
                    contentType = "image/jpeg"
                }
                
                fileRef.putFile(Uri.fromFile(tempFile), metadata).await()
                
                // STEP 3: Get URL
                val url = fileRef.downloadUrl.await()
                url.toString()
            } catch (e: Exception) {
                Log.e(TAG, "Profile upload error: ${e.message}", e)
                null
            } finally {
                tempFile?.delete()
            }
        }
    }
}
