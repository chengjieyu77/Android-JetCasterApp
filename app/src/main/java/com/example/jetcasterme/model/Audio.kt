package com.example.jetcasterme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audios_tbl")
data class Audio(
    @PrimaryKey val uid:Int,
    @ColumnInfo(name="album_count") val albumCount:Int,
    @ColumnInfo(name="audio_name") val audioName:String,
    @ColumnInfo(name = "album_name") val albumName:String,
    @ColumnInfo(name = "release_time") val releaseTime:String,
    @ColumnInfo(name = "play_length") val playLength:Int
)
