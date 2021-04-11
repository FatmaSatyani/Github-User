package com.fatmasatyani.githser.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Github (

    @SerializedName("id")
    var id: Int?= null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("login")
    var username: String? = null,

    @SerializedName("location")
    var location: String? = null,

    @SerializedName("repository")
    var repository: Int? = null,

    @SerializedName("company")
    var company: String? = null,

    @SerializedName("followers")
    var followers: Int?= null,

    @SerializedName("following")
    var following: Int? = null,

    @SerializedName("avatar_url")
    var avatar: String? = null

) : Parcelable