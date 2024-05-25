package com.example.jetcasterme.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetcasterme.model.Album

import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName

import com.example.jetcasterme.model.PlayAudio

import kotlinx.coroutines.flow.Flow

@Dao
interface JetcasterDatabaseDao {
    //audio tabl
    @Query("SELECT * FROM audios_tbl")
    fun getAllAudios(): kotlinx.coroutines.flow.Flow<List<Audio>>

    @Insert
    suspend fun insertAudio(audio: Audio)

    @Update
    suspend fun updateAudio(audio: Audio)

    @Delete
    suspend fun deleteAudio(audio:Audio)

    //album table
    @Query("SELECT * FROM albums_tbl")
    fun getAllAlbums(): kotlinx.coroutines.flow.Flow<List<Album>>

    @Insert
    suspend fun insertAlbum(album: Album)

    @Update
    suspend fun updateAlbum(album: Album)

    @Delete
    suspend fun deleteAlbum(album: Album)

    //collection name table
    @Query("SELECT * FROM collections_tbl")
    fun getAllCollections(): kotlinx.coroutines.flow.Flow<List<CollectionName>>

    @Insert
    suspend fun insertCollection(collectionName: CollectionName)

    @Update
    suspend fun updateCollection(collectionName: CollectionName)

    @Delete
    suspend fun deleteCollection(collectionName: CollectionName)


    //relate to two tables
    @Query("SELECT audios_tbl.id As id, audios_tbl.audio_count AS audioCount, audios_tbl.audio_name As audioName, audios_tbl.album_name As albumName, " +
            "audios_tbl.release_time As releaseTime, audios_tbl.play_length As playLength, audios_tbl.media_url As url , albums_tbl.album_image As albumImage "+
            "FROM audios_tbl,albums_tbl " +
            "WHERE audios_tbl.album_name = albums_tbl.album_name")
    fun getAllPlayAudios(): Flow<List<PlayAudio>>

    @Query("SELECT audios_tbl.id As id,audios_tbl.audio_count AS audioCount, audios_tbl.audio_name As audioName, audios_tbl.album_name As albumName, " +
            "audios_tbl.release_time As releaseTime, audios_tbl.play_length As playLength, audios_tbl.media_url As url,albums_tbl.album_image As albumImage "+
            "FROM audios_tbl,albums_tbl " +
            "WHERE audios_tbl.album_name =:albumName AND albums_tbl.album_name =:albumName")
    fun getPlayAudiosByAlbum(albumName: String):Flow<List<PlayAudio>>



//    @Query("SELECT audios_tbl.audio_count AS audioCount, audios_tbl.audio_name As audioName, audios_tbl.album_name As albumName, " +
//            "audios_tbl.release_time As releaseTime, audios_tbl.play_length As playLength, albums_tbl.album_image As albumImage "+
//            "FROM audios_tbl,albums_tbl " +
//            "WHERE albums_tbl.collection_name =:collectionName")
//    fun getPlayAudiosByCollection(collectionName: String):Flow<List<PlayAudio>>






    @Query("SELECT * FROM albums_tbl WHERE collection_name =:collectionName")
    fun getAlbumsByCollection(collectionName:String):Flow<List<Album>>

    @Query("SELECT * FROM collections_tbl")
    fun getAllCollectionNames():Flow<List<CollectionName>>


}