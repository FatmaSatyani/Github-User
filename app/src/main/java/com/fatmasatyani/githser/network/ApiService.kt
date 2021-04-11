package com.fatmasatyani.githser.network

import com.fatmasatyani.githser.BuildConfig
import com.fatmasatyani.githser.entity.Github
import com.fatmasatyani.githser.entity.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: ${BuildConfig.GITHUB_KEY}")
    suspend fun getUser() : List<Github>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_KEY}")
    suspend fun detailUser(
        @Path("username") username: String
    ) : Github

    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_KEY}, User-Agent: request")
    suspend fun searchUser(
        @Query("q") query: String
    ) : UserSearchResponse

}