package com.fatmasatyani.githser.entity

import com.google.gson.annotations.SerializedName

data class UserDetail (

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("login")
    var username: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("company")
    var company: String? = null,

    @field:SerializedName("public_repos")
    var publicRepository: Int? = null,

    @field:SerializedName("followers")
    var followers: Int? = null,

    @field:SerializedName("following")
    var following: Int? = null,

    @field:SerializedName("avatar_url")
    var avatar: String?= null

)