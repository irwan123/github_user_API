package com.example.githubuserapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class FollowingViewModel : ViewModel() {
    val listUserFollowing = MutableLiveData<ArrayList<User>>()
    fun setListUserFollowing(username: String) {
        ClientRetrofit.apiInstance
            .getFollowing(username)
            .enqueue(object : retrofit2.Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listUserFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Gagal", it) }
                }
            })
    }

    fun getListUserFollowing(): LiveData<ArrayList<User>> {
        return listUserFollowing
    }
}