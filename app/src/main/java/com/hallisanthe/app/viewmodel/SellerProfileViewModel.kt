package com.hallisanthe.app.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.models.UserModel
import com.hallisanthe.app.repository.AuthRepository
import com.hallisanthe.app.firebase.FirebaseAuthManager
import com.hallisanthe.app.firebase.FirebaseStorageManager
import com.hallisanthe.app.repository.ProfileStatsRepository
import com.hallisanthe.app.repository.ProductRepository
import com.hallisanthe.app.room.DatabaseProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SellerProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository()
    private val authManager = FirebaseAuthManager()
    private val statsRepository = ProfileStatsRepository()
    private val productDao = DatabaseProvider.getDatabase(application).productDao()
    private val productRepository = ProductRepository(productDao)

    private val _sellerProfile = MutableStateFlow<UserModel?>(null)
    val sellerProfile: StateFlow<UserModel?> = _sellerProfile.asStateFlow()

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating.asStateFlow()

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess.asStateFlow()

    private val _productCount = MutableStateFlow(0)
    val productCount: StateFlow<Int> = _productCount.asStateFlow()

    private val _orderCount = MutableStateFlow(0)
    val orderCount: StateFlow<Int> = _orderCount.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            val user = authRepository.getSessionUser()
            _sellerProfile.value = user
            
            if (user != null) {
                // Use local count for faster, more reliable updates
                productRepository.getLocalProductCountBySeller(user.uid).collect { count ->
                    _productCount.value = count
                }
            }
        }
        
        viewModelScope.launch {
            val user = authRepository.getSessionUser()
            if (user != null) {
                statsRepository.getOrderCount(user.uid).collect { count ->
                    _orderCount.value = count
                }
            }
        }
    }

    fun uploadProfileImage(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            val current = _sellerProfile.value ?: return@launch
            _isUpdating.value = true
            
            val downloadUrl = FirebaseStorageManager.uploadProfileImage(context, current.uid, imageUri)
            if (downloadUrl != null) {
                updateProfile(
                    fullName = current.fullName,
                    shopName = current.shopName,
                    phone = current.phone,
                    businessAddress = current.businessAddress,
                    profileImageUrl = downloadUrl
                )
            } else {
                _isUpdating.value = false
            }
        }
    }

    fun updateProfile(
        fullName: String,
        shopName: String,
        phone: String,
        businessAddress: String,
        profileImageUrl: String = ""
    ) {
        viewModelScope.launch {
            _isUpdating.value = true
            val current = _sellerProfile.value ?: return@launch
            val updated = current.copy(
                fullName = fullName,
                shopName = shopName,
                phone = phone,
                businessAddress = businessAddress,
                profileImageUrl = if (profileImageUrl.isNotEmpty()) profileImageUrl else current.profileImageUrl
            )
            
            val result = authManager.saveUserToFirestore(updated)
            if (result.isSuccess) {
                _sellerProfile.value = updated
                _updateSuccess.value = true
            }
            _isUpdating.value = false
        }
    }

    fun resetUpdateState() {
        _updateSuccess.value = false
    }
}
