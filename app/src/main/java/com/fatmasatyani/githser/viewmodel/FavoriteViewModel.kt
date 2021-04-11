package com.fatmasatyani.githser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fatmasatyani.githser.entity.FavoriteData
import com.fatmasatyani.githser.repository.UserRepository

class FavoriteViewModel (repository: UserRepository): ViewModel() {

    var favorite: LiveData<List<FavoriteData>> = repository.getFavorite()
}