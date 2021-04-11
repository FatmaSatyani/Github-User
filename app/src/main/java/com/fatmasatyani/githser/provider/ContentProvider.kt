package com.fatmasatyani.githser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.fatmasatyani.githser.database.UserDataBase
import com.fatmasatyani.githser.entity.FavoriteDao

class ContentProvider : ContentProvider() {

    private lateinit var userDao: FavoriteDao

    companion object {
        private const val AUTHORITY = "com.fatmasatyani.githser"
        private const val TABLE_NAME = "favorite_data"
        private const val USERLIST = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(
                AUTHORITY, TABLE_NAME, USERLIST
            )
        }
    }

    override fun onCreate(): Boolean {
        userDao = UserDataBase.getInstance(context as Context). favoriteDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USERLIST -> userDao.getUserListProvider()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }
}