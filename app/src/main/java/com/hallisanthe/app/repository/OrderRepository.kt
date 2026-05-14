package com.hallisanthe.app.repository

import android.util.Log
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.channels.awaitClose

/**
 * OrderRepository: Professional order management with real-time Firestore sync.
 * Using Fully Qualified Names to resolve stubborn IDE reference issues.
 */
class OrderRepository {
    private val tag = "OrderRepository"
    private val ordersCollection = com.hallisanthe.app.firebase.FirebaseManager.firestore.collection("orders")

    /**
     * Listens to real-time order updates for a specific seller.
     */
    fun getOrdersForSeller(sellerId: String): kotlinx.coroutines.flow.Flow<List<com.hallisanthe.app.models.Order>> = kotlinx.coroutines.flow.callbackFlow {
        if (sellerId.isBlank()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        var listener: com.google.firebase.firestore.ListenerRegistration? = null
        try {
            listener = ordersCollection
                .whereEqualTo("sellerId", sellerId)
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e(tag, "Seller orders listener error", error)
                        return@addSnapshotListener
                    }

                    try {
                        val orders = snapshot?.toObjects(com.hallisanthe.app.models.Order::class.java) ?: emptyList()
                        trySend(orders)
                    } catch (e: Exception) {
                        Log.e(tag, "Data mapping error in seller orders", e)
                    }
                }
        } catch (e: Exception) {
            Log.e(tag, "Failed to setup seller orders listener", e)
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    /**
     * Listens to real-time order updates for a specific buyer.
     */
    fun getOrdersForBuyer(buyerId: String): kotlinx.coroutines.flow.Flow<List<com.hallisanthe.app.models.Order>> = kotlinx.coroutines.flow.callbackFlow {
        if (buyerId.isBlank()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        var listener: com.google.firebase.firestore.ListenerRegistration? = null
        try {
            listener = ordersCollection
                .whereEqualTo("buyerId", buyerId)
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e(tag, "Buyer orders listener error", error)
                        return@addSnapshotListener
                    }

                    try {
                        val orders = snapshot?.toObjects(com.hallisanthe.app.models.Order::class.java) ?: emptyList()
                        trySend(orders)
                    } catch (e: Exception) {
                        Log.e(tag, "Data mapping error in buyer orders", e)
                    }
                }
        } catch (e: Exception) {
            Log.e(tag, "Failed to setup buyer orders listener", e)
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    /**
     * Tracks a single order in real-time.
     */
    fun getOrderTracking(orderId: String): kotlinx.coroutines.flow.Flow<com.hallisanthe.app.models.Order?> = kotlinx.coroutines.flow.callbackFlow {
        if (orderId.isBlank()) {
            trySend(null)
            close()
            return@callbackFlow
        }

        var listener: com.google.firebase.firestore.ListenerRegistration? = null
        try {
            listener = ordersCollection.document(orderId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e(tag, "Order tracking listener error", error)
                        return@addSnapshotListener
                    }

                    try {
                        val order = snapshot?.toObject(com.hallisanthe.app.models.Order::class.java)
                        trySend(order)
                    } catch (e: Exception) {
                        Log.e(tag, "Data mapping error in order tracking", e)
                    }
                }
        } catch (e: Exception) {
            Log.e(tag, "Failed to setup tracking listener", e)
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    /**
     * Updates the status of an existing order.
     */
    suspend fun updateOrderStatus(orderId: String, newStatus: String) {
        if (orderId.isBlank()) return
        try {
            ordersCollection.document(orderId)
                .update("orderStatus", newStatus, "updatedAt", com.google.firebase.Timestamp.now())
                .await()
        } catch (e: Exception) {
            Log.e(tag, "Failed to update order status", e)
        }
    }

    /**
     * Persists a new order to Firestore.
     */
    suspend fun createOrder(order: com.hallisanthe.app.models.Order) {
        val finalId = if (order.orderId.isBlank()) { 
            "HS-${System.currentTimeMillis()}-${(100..999).random()}" 
        } else {
            order.orderId
        }
        
        val finalOrder = if (order.orderId.isBlank()) {
            order.copy(orderId = finalId)
        } else {
            order
        }
        
        try {
            ordersCollection.document(finalId).set(finalOrder).await()
        } catch (e: Exception) {
            Log.e(tag, "Failed to create order in Firestore", e)
            throw e
        }
    }
}
