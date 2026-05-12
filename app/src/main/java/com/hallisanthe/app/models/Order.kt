package com.hallisanthe.app.models

import com.google.firebase.Timestamp

enum class OrderStatus {
    PENDING, // Order Placed
    WAITING_CONFIRMATION,
    ACCEPTED,
    PREPARING,
    PACKED,
    OUT_FOR_DELIVERY,
    READY_FOR_PICKUP,
    DELIVERED,
    CANCELLED
}

data class OrderItem(
    val productId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 1,
    val imageUrl: String = "",
    val category: String = ""
)

data class Order(
    val orderId: String = "",
    val buyerId: String = "",
    val sellerId: String = "",
    val items: List<OrderItem> = emptyList(),
    val subtotal: Double = 0.0,
    val gst: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val platformCommission: Double = 0.0,
    val sellerEarnings: Double = 0.0,
    val totalAmount: Double = 0.0, // Keeping for backwards compatibility
    val finalAmount: Double = 0.0,
    val platformFee: Double = 0.0,               // Fee charged to buyer
    val orderStatus: String = OrderStatus.PENDING.name,
    val deliveryType: String = "DELIVERY",       // "DELIVERY" or "PICKUP"
    val paymentMethod: String = "UPI",            // "UPI" or "COD"
    val paymentStatus: String = "PAID",           // "PAID" or "PENDING"
    val transactionId: String = "",
    val estimatedTime: String = "30-45 mins",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
