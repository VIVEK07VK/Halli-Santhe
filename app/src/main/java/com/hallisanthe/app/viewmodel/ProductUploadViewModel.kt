package com.hallisanthe.app.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.firebase.FirebaseStorageManager
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.repository.ProductRepository
import com.hallisanthe.app.room.DatabaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed class UploadState {
    object Idle : UploadState()
    object ProcessingImage : UploadState()
    object SavingProduct : UploadState()
    object Success : UploadState()
    data class Error(val message: String) : UploadState()
}

class ProductUploadViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao = DatabaseProvider.getDatabase(application).productDao()
    private val productRepository = ProductRepository(productDao)

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState.asStateFlow()

    fun uploadProduct(
        name: String,
        description: String,
        priceStr: String,
        stockStr: String,
        category: String,
        unit: String,
        imageUri: Uri?
    ) {
        // Validation
        if (name.isBlank() || priceStr.isBlank()) {
            _uploadState.value = UploadState.Error("Please fill in all required fields.")
            return
        }

        if (imageUri == null) {
            _uploadState.value = UploadState.Error("Please select a product image.")
            return
        }

        val price = priceStr.toDoubleOrNull() ?: 0.0
        val stock = stockStr.toIntOrNull() ?: 0
        val sellerId = FirebaseManager.auth.currentUser?.uid ?: ""
        val sellerName = FirebaseManager.auth.currentUser?.displayName ?: "Artisan Seller"

        if (sellerId.isEmpty()) {
            _uploadState.value = UploadState.Error("User session expired. Please log in again.")
            return
        }

        viewModelScope.launch {
            try {
                // Phase 1: Image Upload
                _uploadState.value = UploadState.ProcessingImage
                
                val imageUrl = FirebaseStorageManager.uploadProductImage(
                    context = getApplication(),
                    sellerId = sellerId,
                    imageUri = imageUri
                )

                // Phase 2: Save Product with URL
                _uploadState.value = UploadState.SavingProduct
                
                val productId = "P-${UUID.randomUUID().toString().take(8).uppercase()}"
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
                    sellerName = sellerName,
                    rating = 4.8,
                    deliveryTime = "2-4 days"
                )

                productRepository.addProduct(product)
                
                _uploadState.value = UploadState.Success
            } catch (e: Exception) {
                _uploadState.value = UploadState.Error(e.message ?: "An unexpected error occurred.")
            }
        }
    }

    fun resetState() {
        _uploadState.value = UploadState.Idle
    }
}
