package com.fatmasatyani.githser.entity

import com.google.gson.annotations.SerializedName

data class UserSearchResponse (

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val userItems: List<Github> = arrayListOf(),

    @field:SerializedName("total_count")
    val totalCount: Int? = null
)