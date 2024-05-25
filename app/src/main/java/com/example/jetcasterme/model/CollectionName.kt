package com.example.jetcasterme.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Objects

@Entity(tableName = "collections_tbl")
data class CollectionName(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val uid:Int,

    @ColumnInfo("collection_name") val collectionName:String,
    //@Ignore val initialSelected:Boolean = false

){
  //var isSelected by mutableStateOf(initialSelected)
}

@Entity(tableName = "collection_society_culture_tbl")
data class SocietyAndCultureCollection(
    @PrimaryKey val id : Int,
    @ColumnInfo("album_name") val albumName:String
)

@Entity(tableName = "collection_news_tbl")
data class NewsCollection(
    @PrimaryKey val id : Int,
    @ColumnInfo("album_name") val albumName:String
)

@Entity(tableName = "collection_arts_tbl")
data class ArtsCollection(
    @PrimaryKey val id : Int,
    @ColumnInfo("album_name") val albumName:String
)

@Entity(tableName = "collection_technology_tbl")
data class TechnologyCollection(
    @PrimaryKey val id : Int,
    @ColumnInfo("album_name") val albumName:String
)

