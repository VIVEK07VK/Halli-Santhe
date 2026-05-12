package com.hallisanthe.app.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.firebase.FirebaseStorageManager
import com.hallisanthe.app.models.Address
import com.hallisanthe.app.models.RecentlyViewedProduct
import com.hallisanthe.app.models.UserModel
import com.hallisanthe.app.repository.AddressRepository
import com.hallisanthe.app.repository.RecentlyViewedRepository
import com.hallisanthe.app.room.DatabaseProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val addressRepository = AddressRepository()
    private val recentlyViewedDao = DatabaseProvider.getDatabase(application).recentlyViewedDao()
    private val recentlyViewedRepository = RecentlyViewedRepository(recentlyViewedDao)

    private val _userProfile = MutableStateFlow<UserModel?>(null)
    val userProfile: StateFlow<UserModel?> = _userProfile

    val addresses: StateFlow<List<Address>> = addressRepository.getAddresses().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val recentlyViewed: StateFlow<List<RecentlyViewedProduct>> = recentlyViewedRepository.recentProducts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val uid = FirebaseManager.auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val snapshot = FirebaseManager.firestore.collection("users").document(uid).get().await()
            _userProfile.value = snapshot.toObject(UserModel::class.java)
        }
    }

    fun updateProfile(
        fullName: String,
        shopName: String,
        businessAddress: String,
        phone: String,
        villageName: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            val uid = FirebaseManager.auth.currentUser?.uid ?: return@launch
            var imageUrl = _userProfile.value?.profileImageUrl ?: ""

            if (imageUri != null) {
                imageUrl = FirebaseStorageManager.uploadProductImage(imageUri) ?: imageUrl
            }

            val updatedUser = _userProfile.value?.copy(
                fullName = fullName,
                shopName = shopName,
                businessAddress = businessAddress,
                phone = phone,
                villageName = villageName,
                profileImageUrl = imageUrl,
                updatedAt = com.google.firebase.Timestamp.now()
            ) ?: UserModel(
                uid = uid,
                fullName = fullName,
                shopName = shopName,
                businessAddress = businessAddress,
                phone = phone,
                villageName = villageName,
                profileImageUrl = imageUrl,
                email = FirebaseManager.auth.currentUser?.email ?: ""
            )

            FirebaseManager.firestore.collection("users").document(uid).set(updatedUser).await()
            _userProfile.value = updatedUser
        }
    }

    fun addAddress(title: String, village: String, landmark: String, pincode: String, phone: String) {
        viewModelScope.launch {
            val address = Address(
                title = title,
                villageTown = village,
                landmark = landmark,
                pincode = pincode,
                phone = phone
            )
            addressRepository.addAddress(address)
        }
    }

    fun deleteAddress(addressId: String) {
        viewModelScope.launch {
            addressRepository.deleteAddress(addressId)
        }
    }

    fun addProductToRecent(product: RecentlyViewedProduct) {
        viewModelScope.launch {
            recentlyViewedRepository.addProductToRecent(product)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            recentlyViewedRepository.clearHistory()
        }
    }
}
