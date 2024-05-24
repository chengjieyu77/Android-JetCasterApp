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

    @ColumnInfo("collection_name") val collectionName:String?,
    //@Ignore val initialSelected:Boolean = false

){
  //var isSelected by mutableStateOf(initialSelected)
}
