package com.fatmasatyani.githser.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_data")
data class FavoriteData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "login")
    val username: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "company")
    val company: String,

    @ColumnInfo(name = "repository")
    val repository: Int,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "avatarUrl")
    val avatar: String
)