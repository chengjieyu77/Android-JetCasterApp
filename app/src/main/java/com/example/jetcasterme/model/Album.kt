package com.example.jetcasterme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums_tbl")
data class Album(
    @PrimaryKey val uid:Int,
    @ColumnInfo(name = "album_name") val albumName:String,
    @ColumnInfo(name = "album_image") val albumImage:String,
    @ColumnInfo(name = "collection_name") val collectionName:String,
)
