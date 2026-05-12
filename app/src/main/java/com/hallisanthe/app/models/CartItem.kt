package com.hallisanthe.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val category: String, // needed for tax calculation
    val unit: String,
    val sellerId: String = ""
)
