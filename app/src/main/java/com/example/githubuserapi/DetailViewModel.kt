package com.example.githubuserapi
import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserRespons>()
    private var userDao: FavoriteUserDao?
    private var userDb: DatabaseUser?
    init {
        userDb = DatabaseUser.getDatabase(application)
        userDao = userDb?.favoriteUser()
    }
    fun setDetailUser(username: String){
        ClientRetrofit.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserRespons>{
                override fun onResponse(
                    call: Call<DetailUserRespons>,
                    response: Response<DetailUserRespons>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserRespons>, t: Throwable) {
                    t.message?.let { Log.d("Gagal", it) }
                }

            })
    }
    fun getDetailUser(): LiveData<DetailUserRespons>{
        return user
    }
    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }
    suspend fun checkUser(id: Int) = userDao?.checkUser(id)
    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}