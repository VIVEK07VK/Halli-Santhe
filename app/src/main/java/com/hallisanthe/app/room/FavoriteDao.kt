package com.hallisanthe.app.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hallisanthe.app.models.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE productId = :productId")
    suspend fun deleteFavoriteById(productId: String)

    @Query("SELECT COUNT(*) FROM favorites WHERE productId = :productId")
    suspend fun isFavorite(productId: String): Int
}
