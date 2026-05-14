package com.hallisanthe.app.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.hallisanthe.app.firebase.FirebaseManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProfileStatsRepository {
    private val firestore = FirebaseManager.firestore

    fun getProductCount(sellerId: String): Flow<Int> = callbackFlow {
        val listener = firestore.collection("products")
            .whereEqualTo("sellerId", sellerId)
            .addSnapshotListener { snapshot, _ ->
                val count = snapshot?.size() ?: 0
                trySend(count)
            }
        awaitClose { listener.remove() }
    }

    fun getOrderCount(sellerId: String): Flow<Int> = callbackFlow {
        val listener = firestore.collection("orders")
            .whereEqualTo("sellerId", sellerId)
            .addSnapshotListener { snapshot, _ ->
                val count = snapshot?.size() ?: 0
                trySend(count)
            }
        awaitClose { listener.remove() }
    }
}
