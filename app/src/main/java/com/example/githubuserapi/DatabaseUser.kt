package com.example.githubuserapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class DatabaseUser: RoomDatabase() {
    companion object{
        var DATABASE_USER : DatabaseUser? = null
        fun getDatabase(context: Context): DatabaseUser?{
            if (DATABASE_USER==null){
                synchronized(DatabaseUser::class){
                    DATABASE_USER = Room.databaseBuilder(context.applicationContext, DatabaseUser::class.java, "user database").build()
                }
            }
            return DATABASE_USER
        }
    }
    abstract fun favoriteUser(): FavoriteUserDao
}