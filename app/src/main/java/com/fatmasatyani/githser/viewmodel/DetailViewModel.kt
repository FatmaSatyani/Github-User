package com.fatmasatyani.githser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fatmasatyani.githser.entity.DataMapper
import com.fatmasatyani.githser.entity.FavoriteData
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.entity.UserDetail
import com.fatmasatyani.githser.repository.UserRepository
import com.fatmasatyani.githser.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: UserRepository, val github: Github): ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    lateinit var detailUser: LiveData<Resource<Github>>
    private var favorite: FavoriteData? = null
    private var isFromFavoriteFragment = false
    var isFavorite = MutableLiveData(false)

    init {
        getdetailUser()
        github.id?.let { checkIsFavorite(it) }
    }

    private fun getdetailUser() {
        detailUser = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.getDetailUsers(github.username ?: "")))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
            }
        }
    }

    private fun checkIsFavorite(i: Int) {
        CoroutineScope(GlobalScope.coroutineContext).launch {
            favorite = repository.getSingleFavorite(i)
            if (favorite == null || i == 0) {
                isFavorite.postValue(false)
            } else {
                isFromFavoriteFragment = true
                isFavorite.postValue(true)
            }
        }
    }

    fun addToFavorite(users: UserDetail) =
        CoroutineScope(GlobalScope.coroutineContext).launch {
            repository.addFavorite(
                DataMapper.singleDetailUserToFavorite(users)
            )
            isFavorite.postValue(true)
        }

    fun removeFavorite() {
        CoroutineScope(GlobalScope.coroutineContext).launch {
            val favo = favorite
            if (favo != null) {
                repository.removeFavorite(favo)
            } else {
            }
            isFavorite.postValue(false)
        }
    }
}
