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
    @Query("SELECT * FROM audios_tbl")
    suspend fun getAllAudios():List<Audio>

    @Query("SELECT * FROM audios_tbl WHERE album_name =:album")
    suspend fun getAudiosByAlbum(album:String):List<Audio>

    @Query("SELECT audios_tbl.audio_count AS audioCount, audios_tbl.album_name As audioName, audios_tbl.album_name As albumName, " +
            "audios_tbl.release_time As releaseTime, audios_tbl.play_length As playLength, albums_tbl.album_image As albumImage "+
            "FROM audios_tbl,albums_tbl " +
            "WHERE audios_tbl.album_name = albums_tbl.album_name")
    suspend fun getAllPlayAudios(): List<PlayAudio>

    @Query("SELECT audios_tbl.audio_count AS audioCount, audios_tbl.album_name As audioName, audios_tbl.album_name As albumName, " +
            "audios_tbl.release_time As releaseTime, audios_tbl.play_length As playLength, albums_tbl.album_image As albumImage "+
            "FROM audios_tbl,albums_tbl " +
            "WHERE audios_tbl.album_name =:albumName AND albums_tbl.album_name =:albumName")
    suspend fun getPlayAudiosByAlbum(albumName: String):List<PlayAudio>

    @Insert
    suspend fun insertAudio(audio: Audio)

    @Update
    suspend fun updateAudio(audio: Audio)

    @Delete
    suspend fun deleteAllAudios(audio:Audio)

//    @Query("SELECT collection_albums FROM collections_tbl WHERE collection_name=:collection")
//    suspend fun getAlbumsByCollection(collection: String):List<Album>

    @Query("SELECT * FROM albums_tbl WHERE collection_name =:collectionName")
    suspend fun getAlbumsByCollection(collectionName:String):List<Album>

    @Query("SELECT * FROM collections_tbl")
    suspend fun getAllCollectionNames():List<CollectionName>

}