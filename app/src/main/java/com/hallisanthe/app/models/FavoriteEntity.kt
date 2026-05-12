package com.hallisanthe.app.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Standalone Room entity for persisted favorites.
 * Mirrors the fields of [Product] that we need to display the Favorites screen
 * without re-querying Firestore. Decoupled from the products table so that
 * deleting / updating products doesn't wipe favorites.
 */
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val productId: String,
    val name: String,
    val description: String = "",
    val price: Double,
    val stock: Int = 0,
    val category: String = "",
    val sellerName: String = "",
    val sellerId: String = "",
    val imageUrl: String,
    val unit: String = "kg",
    val rating: Double = 4.0,
    val discountPercent: Int? = null,
    val tag: String? = null
) {
    /** Convert back to a [Product] for UI reuse */
    fun toProduct() = Product(
        id            = productId,
        name          = name,
        description   = description,
        price         = price,
        stock         = stock,
        category      = category,
        sellerName    = sellerName,
        sellerId      = sellerId,
        imageUrl      = imageUrl,
        unit          = unit,
        rating        = rating,
        discountPercent = discountPercent,
        tag           = tag,
        isFavorite    = true
    )

    companion object {
        fun fromProduct(product: Product) = FavoriteEntity(
            productId     = product.id,
            name          = product.name,
            description   = product.description,
            price         = product.price,
            stock         = product.stock,
            category      = product.category,
            sellerName    = product.sellerName,
            sellerId      = product.sellerId,
            imageUrl      = product.imageUrl,
            unit          = product.unit,
            rating        = product.rating,
            discountPercent = product.discountPercent,
            tag           = product.tag
        )
    }
}
