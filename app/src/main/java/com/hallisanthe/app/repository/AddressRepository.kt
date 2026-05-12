package com.hallisanthe.app.repository

import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.models.Address
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AddressRepository {
    private val firestore = FirebaseManager.firestore
    private val auth = FirebaseManager.auth

    fun getAddresses(): Flow<List<Address>> = callbackFlow {
        val userId = auth.currentUser?.uid ?: ""
        val listener = firestore.collection("users").document(userId).collection("addresses")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val addresses = snapshot?.toObjects(Address::class.java) ?: emptyList()
                trySend(addresses)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addAddress(address: Address) {
        val userId = auth.currentUser?.uid ?: ""
        val docRef = firestore.collection("users").document(userId).collection("addresses").document()
        val finalAddress = address.copy(id = docRef.id, userId = userId)
        docRef.set(finalAddress).await()
    }

    suspend fun updateAddress(address: Address) {
        val userId = auth.currentUser?.uid ?: ""
        firestore.collection("users").document(userId).collection("addresses").document(address.id).set(address).await()
    }

    suspend fun deleteAddress(addressId: String) {
        val userId = auth.currentUser?.uid ?: ""
        firestore.collection("users").document(userId).collection("addresses").document(addressId).delete().await()
    }
}
