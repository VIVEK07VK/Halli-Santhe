package com.hallisanthe.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_viewed")
data class RecentlyViewedProduct(
    @PrimaryKey
    val productId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0,
    val category: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
