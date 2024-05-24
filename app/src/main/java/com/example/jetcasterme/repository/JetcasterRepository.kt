package com.example.jetcasterme.repository

import com.example.jetcasterme.data.JetcasterDatabaseDao
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName
import com.example.jetcasterme.model.PlayAudio


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JetcasterRepository @Inject constructor(private val jetcasterDatabaseDao: JetcasterDatabaseDao){

     fun getAllPlayAudios(): Flow<List<PlayAudio>>
    = jetcasterDatabaseDao.getAllPlayAudios()


     fun getPlayAudiosByAlbum(albumName:String): Flow<List<PlayAudio>>
    = jetcasterDatabaseDao.getPlayAudiosByAlbum(albumName = albumName)

     //audio
     suspend fun addAudio(audio: Audio)
     = jetcasterDatabaseDao.insertAudio(audio)

     suspend fun updateAudio(audio: Audio)
     = jetcasterDatabaseDao.updateAudio(audio)

     suspend fun deleteAudio(audio: Audio)
             = jetcasterDatabaseDao.deleteAudio(audio)

     //album
     suspend fun addAlbum(album: Album)
     = jetcasterDatabaseDao.insertAlbum(album)

     suspend fun updateAlbum(album: Album)
             = jetcasterDatabaseDao.updateAlbum(album)

     suspend fun deleteAlbum(album: Album)
             = jetcasterDatabaseDao.deleteAlbum(album)

     fun getAllAlbums()
     = jetcasterDatabaseDao.getAllAlbums()

     //collection name
     suspend fun addCollectionName(collectionName: CollectionName)
             = jetcasterDatabaseDao.insertCollection(collectionName)

     suspend fun updateCollectionName(collectionName: CollectionName)
             = jetcasterDatabaseDao.updateCollection(collectionName)

     suspend fun deleteCollectionName(collectionName: CollectionName)
             = jetcasterDatabaseDao.deleteCollection(collectionName)

     fun getAllCollectionNames():Flow<List<CollectionName>>
     =jetcasterDatabaseDao.getAllCollectionNames()



    fun getAlbumsByCollection(collectionName:String):Flow<List<Album>>
    =jetcasterDatabaseDao.getAlbumsByCollection(collectionName = collectionName)



//
//    suspend fun getAllCollectionNames():List<CollectionName>
//    =jetcasterDatabaseDao.getAllCollectionNames()
}