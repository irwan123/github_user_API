package com.example.githubuserapi

data class DetailUserRespons(
    val login : String,
    val id : Int,
    val name : String,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val followers : Int,
    val following : Int,
)
