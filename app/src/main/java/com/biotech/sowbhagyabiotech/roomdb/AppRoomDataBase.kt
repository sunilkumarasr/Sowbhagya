package com.biotech.sowbhagyabiotech.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItems::class], version = 1, exportSchema = false)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun cropTableDao(): CartDAO

    companion object {
        private var INSTANCE: AppRoomDataBase? = null

        fun getInstance(context: Context): AppRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDataBase::class.java,
                    "sowbhagya"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}