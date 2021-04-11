package com.fatmasatyani.githser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.repository.UserRepository

class ViewModelFactory (
    private val repository: UserRepository,
    private val any: Any? = null,
    private val any1: Any? = null
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(repository) as T
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java))
            return FavoriteViewModel(repository) as T
        if(modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(repository, any as Github) as T
        throw IllegalArgumentException()
    }
}