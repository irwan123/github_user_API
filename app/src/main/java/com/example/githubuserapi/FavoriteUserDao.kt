package com.example.githubuserapi

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoriteUser: FavoriteUser)
    @Query("Select * From favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>
    @Query("Select count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int
    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id:Int):Int
    @Query("Select * From favorite_user")
    fun findAll(): Cursor
}