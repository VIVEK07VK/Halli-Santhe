package com.hallisanthe.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    val category: String = "",
    val sellerName: String = "",
    val sellerId: String = "",
    val imageUrl: String = "",
    // Additional fields for UI
    val unit: String = "kg",
    val rating: Double = 4.0,
    val discountPercent: Int? = null,
    val tag: String? = null,
    val isFavorite: Boolean = false
)

data class Category(
    val id: String,
    val name: String,
    val iconResOrUrl: String // Can be a local drawable name or emoji for dummy data
)
