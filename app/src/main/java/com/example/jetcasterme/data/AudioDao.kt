package com.example.jetcasterme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetcasterme.model.Audio

@Dao
interface AudioDao {
    @Query("SELECT * FROM audios_tbl")
    suspend fun getAllAudios():List<Audio>

    @Query("SELECT * FROM audios_tbl WHERE album_name =:album")
    suspend fun findAudiosByAlbum(album:String):List<Audio>

    @Insert
    fun insertAudio(audio: Audio)

    @Update
    fun updateAudio(audio: Audio)

    @Delete
    fun deleteAll()
}