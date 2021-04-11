package com.fatmasatyani.githser.entity

object DataMapper {

    fun singleFavoriteToUser(item: FavoriteData) =
        Github(
            id = item.id,
            username = item.username
        )

    fun singleDetailUserToFavorite(item: UserDetail) =
        FavoriteData (
            item.id ?: 0,
            item.username ?:"",
            item.name?: "",
            item.location ?: "",
            item.company ?: "",
            item.publicRepository?: 0,
            item.followers?: 0,
            item.following?: 0,
            item.avatar?: ""
        )
}