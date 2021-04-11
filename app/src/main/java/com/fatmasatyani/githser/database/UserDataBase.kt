package com.fatmasatyani.githser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fatmasatyani.githser.entity.FavoriteDao
import com.fatmasatyani.githser.entity.FavoriteData

@Database(entities = [FavoriteData::class], version = 1, exportSchema = false)
abstract class UserDataBase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase {
            val tempInstance = INSTANCE
            if(tempInstance!= null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}