package com.hallisanthe.app.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.firebase.FirebaseStorageManager
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.repository.ProductRepository
import com.hallisanthe.app.room.DatabaseProvider
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    object Success : UploadState()
    data class Error(val message: String) : UploadState()
}

class ProductUploadViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao = DatabaseProvider.getDatabase(application).productDao()
    private val productRepository = ProductRepository(productDao)

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState

    fun uploadProduct(
        name: String,
        description: String,
        priceStr: String,
        stockStr: String,
        category: String,
        unit: String,
        imageUri: Uri?
    ) {
        val price = priceStr.toDoubleOrNull() ?: 0.0
        val stock = stockStr.toIntOrNull() ?: 0
        val sellerId = FirebaseManager.auth.currentUser?.uid ?: ""
        val sellerName = FirebaseManager.auth.currentUser?.displayName ?: "Artisan Seller"

        if (name.isBlank()) {
            _uploadState.value = UploadState.Error("Product name is required")
            return
        }
        if (price <= 0) {
            _uploadState.value = UploadState.Error("Please enter a valid price")
            return
        }
        if (imageUri == null) {
            _uploadState.value = UploadState.Error("Please select a product image")
            return
        }

        viewModelScope.launch {
            _uploadState.value = UploadState.Loading
            try {
                // 1. Upload Image to Storage
                val imageUrl = FirebaseStorageManager.uploadProductImage(imageUri)
                if (imageUrl == null) {
                    _uploadState.value = UploadState.Error("Image upload failed")
                    return@launch
                }

                // 2. Create Product Object
                val productId = UUID.randomUUID().toString()
                val product = Product(
                    id = productId,
                    name = name.trim(),
                    description = description.trim(),
                    price = price,
                    stock = stock,
                    category = category,
                    unit = unit,
                    imageUrl = imageUrl,
                    sellerId = sellerId,
                    sellerName = sellerName
                )

                // 3. Save to Firestore
                productRepository.addProduct(product)
                
                _uploadState.value = UploadState.Success
            } catch (e: Exception) {
                _uploadState.value = UploadState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun resetState() {
        _uploadState.value = UploadState.Idle
    }
}
