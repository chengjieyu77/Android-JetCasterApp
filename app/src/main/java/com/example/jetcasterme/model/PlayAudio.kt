package com.example.jetcasterme.model




data class PlayAudio(
    val id:Int?,
    val audioCount:Int,
    val audioName:String,
    val releaseTime:String,
    val playLength:Int,
    val albumName:String,
    val albumImage:String?,
    val url:String?
)
