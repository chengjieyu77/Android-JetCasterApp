package com.example.jetcasterme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections_tbl")
data class CollectionName(
    @PrimaryKey val uid:Int,
    @ColumnInfo("collection_name") val collectionName:String,
)
