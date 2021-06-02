package com.example.anotheruserapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var listData = MutableLiveData<ArrayList<User>>()
    fun setFavoriteUser(context: Context){
        val cursorMapping = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumns.CONTENT_URI,
                null,
                null,
                null,
                null
        )
        val listKonversi = MappingHelper.mapCursorToArrayList(cursorMapping)
        listData.postValue(listKonversi)
    }
    fun getFavoriteUser(): LiveData<ArrayList<User>> {
        return listData
    }
}