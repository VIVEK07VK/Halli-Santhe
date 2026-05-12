package com.hallisanthe.app.repository

import com.google.firebase.firestore.ListenerRegistration
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.models.Order
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class OrderRepository {
    private val ordersCollection = FirebaseManager.firestore.collection("orders")

    fun getOrdersForSeller(sellerId: String): Flow<List<Order>> = callbackFlow {
        var listener: ListenerRegistration? = null
        try {
            listener = ordersCollection
                .whereEqualTo("sellerId", sellerId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val orders = snapshot?.toObjects(Order::class.java) ?: emptyList()
                    trySend(orders.sortedByDescending { it.createdAt })
                }
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    fun getOrdersForBuyer(buyerId: String): Flow<List<Order>> = callbackFlow {
        var listener: ListenerRegistration? = null
        try {
            listener = ordersCollection
                .whereEqualTo("buyerId", buyerId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val orders = snapshot?.toObjects(Order::class.java) ?: emptyList()
                    trySend(orders.sortedByDescending { it.createdAt })
                }
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    fun getOrderTracking(orderId: String): Flow<Order?> = callbackFlow {
        var listener: ListenerRegistration? = null
        try {
            listener = ordersCollection.document(orderId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val order = snapshot?.toObject(Order::class.java)
                    trySend(order)
                }
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    suspend fun updateOrderStatus(orderId: String, newStatus: String) {
        try {
            ordersCollection.document(orderId).update("orderStatus", newStatus).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun createOrder(order: Order) {
        try {
            ordersCollection.document(order.orderId).set(order).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
