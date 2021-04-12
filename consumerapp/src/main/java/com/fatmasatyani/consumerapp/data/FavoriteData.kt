package com.fatmasatyani.consumerapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FavoriteData (
    val username: String?,
    val avatarUrl: String
        ) : Parcelable