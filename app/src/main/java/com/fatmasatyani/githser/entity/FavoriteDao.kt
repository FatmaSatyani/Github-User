package com.fatmasatyani.githser.entity

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_data")
    fun getFavorites(): LiveData<List<FavoriteData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: FavoriteData)

    @Query("SELECT * FROM favorite_data WHERE id=:id")
    fun getSingleFavorite(id: Int): FavoriteData

//    @Query("SELECT * FROM favorite_data ORDER BY login ASC")
//    fun getUserListProvider(): Cursor

    @Query ("SELECT * FROM favorite_data")
    fun getUserListProvider(): Cursor

    @Delete
    fun removeFavorite(favorite: FavoriteData)
}