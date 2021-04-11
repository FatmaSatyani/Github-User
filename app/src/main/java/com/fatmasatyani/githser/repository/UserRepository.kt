package com.fatmasatyani.githser.repository

import com.fatmasatyani.githser.entity.FavoriteDao
import com.fatmasatyani.githser.entity.FavoriteData
import com.fatmasatyani.githser.network.RetrofitBuilder

class UserRepository(private val dao: FavoriteDao) {

    private val api = RetrofitBuilder.apiService

    suspend fun getUsers() = api.getUser()
    suspend fun getDetailUsers(username: String) = api.detailUser(username)
    suspend fun searchUser(query: String) = api.searchUser(query)

    fun getFavorite() = dao.getFavorites()
    fun getSingleFavorite(id: Int) = dao.getSingleFavorite(id)
    fun addFavorite (favorite: FavoriteData) = dao.addFavorite(favorite)
    fun removeFavorite(favorite: FavoriteData) = dao.removeFavorite(favorite)
}