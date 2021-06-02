package com.example.githubuserapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientRetrofit {
    private const val URL_BASE = "https://api.github.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInstance = retrofit.create(API::class.java)
}