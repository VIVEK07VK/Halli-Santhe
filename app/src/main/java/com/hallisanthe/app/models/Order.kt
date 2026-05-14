package com.hallisanthe.app.models

import com.google.firebase.Timestamp

enum class OrderStatus {
    PENDING,               // Order Placed
    WAITING_CONFIRMATION,  // Waiting for Seller
    ACCEPTED,              // Seller Accepted
    PREPARING,             // Preparing Order
    READY_FOR_PICKUP,      // Ready for Pickup
    OUT_FOR_DELIVERY,      // Out for Delivery
    DELIVERED,             // Delivered
    CANCELLED,             // Cancelled
    REJECTED               // Seller Rejected
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
    val buyerName: String = "",
    val buyerPhone: String = "",
    val sellerId: String = "",
    val sellerName: String = "",
    val items: List<OrderItem> = emptyList(),
    val subtotal: Double = 0.0,
    val gst: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val platformFee: Double = 0.0,
    val platformCommission: Double = 0.0,
    val finalAmount: Double = 0.0,
    val totalAmount: Double = 0.0, // For compatibility
    val sellerEarnings: Double = 0.0,
    val orderStatus: String = OrderStatus.PENDING.name,
    val deliveryType: String = "DELIVERY", // "DELIVERY" or "PICKUP"
    val deliveryAddress: String = "",
    val paymentMethod: String = "COD", // "UPI" or "COD"
    val paymentStatus: String = "PENDING", // "PAID" or "PENDING"
    val transactionId: String = "",
    val estimatedTime: String = "30-45 mins",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)
