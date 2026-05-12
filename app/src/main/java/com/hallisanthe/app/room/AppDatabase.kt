package com.hallisanthe.app.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hallisanthe.app.models.CartItem
import com.hallisanthe.app.models.FavoriteEntity
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.models.RecentlyViewedProduct

@Database(
    entities = [Product::class, CartItem::class, FavoriteEntity::class, RecentlyViewedProduct::class, RecentSearchEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun recentlyViewedDao(): RecentlyViewedDao
    abstract fun recentSearchDao(): RecentSearchDao
}
