package com.hallisanthe.app.repository

import com.hallisanthe.app.models.RecentlyViewedProduct
import com.hallisanthe.app.room.RecentlyViewedDao
import kotlinx.coroutines.flow.Flow

class RecentlyViewedRepository(private val recentlyViewedDao: RecentlyViewedDao) {
    val recentProducts: Flow<List<RecentlyViewedProduct>> = recentlyViewedDao.getRecentProducts()

    suspend fun addProductToRecent(product: RecentlyViewedProduct) {
        recentlyViewedDao.insertProduct(product)
    }

    suspend fun clearHistory() {
        recentlyViewedDao.clearAll()
    }
}
