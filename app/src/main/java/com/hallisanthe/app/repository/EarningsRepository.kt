package com.hallisanthe.app.repository

import com.google.firebase.firestore.ListenerRegistration
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.models.Order
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class EarningsRepository {
    private val ordersCollection = FirebaseManager.firestore.collection("orders")

    fun getCompletedOrdersForSeller(sellerId: String): Flow<List<Order>> = callbackFlow {
        var listener: ListenerRegistration? = null
        try {
            listener = ordersCollection
                .whereEqualTo("sellerId", sellerId)
                .whereEqualTo("orderStatus", "DELIVERED")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        error.printStackTrace()
                        close()
                        return@addSnapshotListener
                    }
                    val orders = snapshot?.toObjects(Order::class.java) ?: emptyList()
                    trySend(orders)
                }
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { listener?.remove() }
    }
}
