package com.example.githubuserapi

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

open class ViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()
    fun setSearchUser (query: String){
        ClientRetrofit.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserRespons>{
                override fun onResponse(call: Call<UserRespons>, response: Response<UserRespons>) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserRespons>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }
    fun getSearchUser(): LiveData<ArrayList<User>>{
        return listUser
    }
}