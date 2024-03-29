package com.example.githubuserapi

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.service.autofill.UserData

class UserContentProvider : ContentProvider() {
    companion object{
        const val AUTHORITY = "com.example.githubuserapi"
        const val TABLE_NAME = "favorite_user"
        const val FAVORITE_ID = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_ID)
        }
    }
    private lateinit var userDao: FavoriteUserDao


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
       return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }
    override fun onCreate(): Boolean {
        userDao = context?.let { ctx ->
            DatabaseUser.getDatabase(ctx)?.favoriteUser()
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when(uriMatcher.match(uri)){
            FAVORITE_ID -> {
                cursor = userDao.findAll()
                if (context != null){
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
