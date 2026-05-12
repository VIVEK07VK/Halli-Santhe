package com.hallisanthe.app.room

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "hallisanthe_database"
            )
            .fallbackToDestructiveMigration()
            .build()
            INSTANCE = instance
            instance
        }
    }
}
