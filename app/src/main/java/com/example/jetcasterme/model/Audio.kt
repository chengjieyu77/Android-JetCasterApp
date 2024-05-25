package com.example.jetcasterme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audios_tbl")
data class Audio(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val uid:Int,
    @ColumnInfo(name="audio_count") val audioCount:Int,
    @ColumnInfo(name="audio_name") val audioName:String,
    @ColumnInfo(name = "release_time") val releaseTime:String,
    @ColumnInfo(name = "play_length") val playLength:Int,
    @ColumnInfo(name = "album_name") val albumName:String,
    @ColumnInfo(name = "media_url") val mediaUrl:String,
    @ColumnInfo(name="collection_name") val collectionName: String?
)


