package com.fatmasatyani.githser.viewmodel

import androidx.lifecycle.*
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.entity.UserSearchResponse
import com.fatmasatyani.githser.repository.UserRepository
import com.fatmasatyani.githser.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository): ViewModel() {

    val user: LiveData<Resource<List<Github>>> = user()
    val userSearched = MutableLiveData<Resource<UserSearchResponse>>()

    private fun user() = liveData(Dispatchers.IO)  {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Ouccred!"))
        }
    }

    fun searchUser (query: String) = viewModelScope.launch(Dispatchers.IO) {
        userSearched.postValue((Resource.loading(data = null)))
        try {
            val searchResponse = repository.searchUser(query)
            userSearched.postValue(Resource.success(data = searchResponse))
        } catch (exception: Exception) {
            userSearched.postValue(
                Resource.error(data = null, message = exception.message?: "Error Occured!")
            )
        }
    }
}