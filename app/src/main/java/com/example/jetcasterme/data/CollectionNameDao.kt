package com.example.jetcasterme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.CollectionName

@Dao
interface CollectionNameDao {
    @Query("SELECT * FROM audios_tbl")
    fun getAllCollections(): kotlinx.coroutines.flow.Flow<List<CollectionName>>

    @Insert
    suspend fun insertCollection(collectionName: CollectionName)

    @Update
    suspend fun updateCollection(collectionName: CollectionName)

    @Delete
    suspend fun deleteAllCollections(collectionName: CollectionName)
}