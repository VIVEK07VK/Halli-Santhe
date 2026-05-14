package com.hallisanthe.app.repository

import com.google.firebase.firestore.ListenerRegistration
import com.hallisanthe.app.firebase.FirebaseManager
import com.hallisanthe.app.models.Inquiry
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class InquiryRepository {
    private val inquiriesCollection = FirebaseManager.firestore.collection("inquiries")

    suspend fun sendInquiry(inquiry: Inquiry) {
        try {
            val inquiryId = UUID.randomUUID().toString()
            val newInquiry = inquiry.copy(inquiryId = inquiryId)
            inquiriesCollection.document(inquiryId).set(newInquiry).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getInquiriesForSeller(sellerId: String): Flow<List<Inquiry>> = callbackFlow {
        var listener: ListenerRegistration? = null
        try {
            listener = inquiriesCollection
                .whereEqualTo("sellerId", sellerId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        error.printStackTrace()
                        close()
                        return@addSnapshotListener
                    }
                    val inquiries = snapshot?.toObjects(Inquiry::class.java) ?: emptyList()
                    trySend(inquiries.sortedByDescending { it.timestamp })
                }
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    suspend fun respondToInquiry(inquiryId: String, responseStatus: String) {
        try {
            inquiriesCollection.document(inquiryId).update("responseStatus", responseStatus).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
