package com.hallisanthe.app.repository

import com.hallisanthe.app.models.FavoriteEntity
import com.hallisanthe.app.models.Product
import com.hallisanthe.app.room.FavoriteDao
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val allFavorites: Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    suspend fun toggleFavorite(product: Product): Boolean {
        val isCurrentlyFav = favoriteDao.isFavorite(product.id) > 0
        if (isCurrentlyFav) {
            favoriteDao.deleteFavoriteById(product.id)
        } else {
            favoriteDao.insertFavorite(FavoriteEntity.fromProduct(product))
        }
        return !isCurrentlyFav
    }

    suspend fun isFavorite(productId: String): Boolean {
        return favoriteDao.isFavorite(productId) > 0
    }

    suspend fun removeFavorite(productId: String) {
        favoriteDao.deleteFavoriteById(productId)
    }
}
