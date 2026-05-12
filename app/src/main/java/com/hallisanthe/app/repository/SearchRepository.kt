package com.hallisanthe.app.repository

import com.hallisanthe.app.room.RecentSearchDao
import com.hallisanthe.app.room.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

class SearchRepository(private val recentSearchDao: RecentSearchDao) {

    fun getRecentSearches(): Flow<List<RecentSearchEntity>> = recentSearchDao.getRecentSearches()

    suspend fun saveSearch(query: String) {
        if (query.isNotBlank()) {
            recentSearchDao.insertSearch(RecentSearchEntity(query.trim()))
        }
    }

    suspend fun deleteSearch(query: String) {
        recentSearchDao.deleteSearch(query)
    }

    suspend fun clearHistory() {
        recentSearchDao.clearAll()
    }
}
