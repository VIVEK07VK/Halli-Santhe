package com.hallisanthe.app.models

import com.google.firebase.Timestamp

enum class InquiryStatus {
    PENDING,
    AVAILABLE,
    OUT_OF_STOCK
}

data class Inquiry(
    val inquiryId: String = "",
    val buyerId: String = "",
    val sellerId: String = "",
    val productId: String = "",
    val productName: String = "",
    val responseStatus: String = InquiryStatus.PENDING.name,
    val timestamp: Timestamp = Timestamp.now()
)
