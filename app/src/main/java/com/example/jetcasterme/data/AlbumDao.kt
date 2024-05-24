package com.example.jetcasterme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.Audio

@Dao
interface AlbumDao {
    @Query("SELECT * FROM audios_tbl")
    fun getAllAlbums(): kotlinx.coroutines.flow.Flow<List<Album>>

    @Insert
    suspend fun insertAlbum(album: Album)

    @Update
    suspend fun updateAlbum(album: Album)

    @Delete
    suspend fun deleteAllAlbums(album: Album)
}