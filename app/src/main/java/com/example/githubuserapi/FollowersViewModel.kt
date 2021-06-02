package com.example.githubuserapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class FollowersViewModel: ViewModel() {
    val listUserFollowers = MutableLiveData<ArrayList<User>>()
    fun setListUserFollowers(username: String){
        ClientRetrofit.apiInstance
            .getFollowers(username)
            .enqueue(object : retrofit2.Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listUserFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Gagal", it) }
                }
            })
    }
    fun getListUserFollowers(): LiveData<ArrayList<User>>{
        return listUserFollowers
    }
}