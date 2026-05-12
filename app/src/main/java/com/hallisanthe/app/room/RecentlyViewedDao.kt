package com.hallisanthe.app.room

import androidx.room.*
import com.hallisanthe.app.models.RecentlyViewedProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentlyViewedDao {
    @Query("SELECT * FROM recently_viewed ORDER BY timestamp DESC LIMIT 20")
    fun getRecentProducts(): Flow<List<RecentlyViewedProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: RecentlyViewedProduct)

    @Delete
    suspend fun deleteProduct(product: RecentlyViewedProduct)

    @Query("DELETE FROM recently_viewed")
    suspend fun clearAll()
}
