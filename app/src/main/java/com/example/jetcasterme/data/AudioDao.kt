package com.example.jetcasterme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetcasterme.model.Audio
import java.util.concurrent.Flow

@Dao
interface AudioDao {
//    @Query("SELECT * FROM audios_tbl")
//    fun getAllAudios():Flow<List<Audio>>
//
//    @Query("SELECT * FROM audios_tbl WHERE album_name =:album")
//    fun findAudiosByAlbum(album:String):Flow<List<Audio>>
    @Query("SELECT * FROM audios_tbl")
    fun getAllAudios(): kotlinx.coroutines.flow.Flow<List<Audio>>

    @Insert
    suspend fun insertAudio(audio: Audio)

    @Update
    suspend fun updateAudio(audio: Audio)

    @Delete
    suspend fun deleteAllAudios(audio:Audio)


}