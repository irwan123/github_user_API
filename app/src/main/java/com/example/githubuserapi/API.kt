package com.example.githubuserapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserRespons>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ):Call<DetailUserRespons>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}