package com.example.jetcasterme.repository

import com.example.jetcasterme.data.JetcasterDatabaseDao
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.CollectionName
import com.example.jetcasterme.model.PlayAudio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class JetcasterRepository @Inject constructor(private val jetcasterDatabaseDao: JetcasterDatabaseDao){

    suspend fun getAllPlayAudios(): List<PlayAudio>
    = jetcasterDatabaseDao.getAllPlayAudios()


    suspend fun getPlayAudiosByAlbum(albumName:String):List<PlayAudio>
    = jetcasterDatabaseDao.getPlayAudiosByAlbum(albumName = albumName)


    suspend fun getAlbumsByCollection(collectionName:String):List<Album>
    =jetcasterDatabaseDao.getAlbumsByCollection(collectionName = collectionName)

    suspend fun getAllCollectionNames():List<CollectionName>
    =jetcasterDatabaseDao.getAllCollectionNames()
}