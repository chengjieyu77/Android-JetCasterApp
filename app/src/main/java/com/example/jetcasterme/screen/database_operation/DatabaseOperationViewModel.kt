package com.example.jetcasterme.screen.database_operation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName
import com.example.jetcasterme.repository.JetcasterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseOperationViewModel@Inject constructor(private val repository: JetcasterRepository):
    ViewModel(){
    fun addAudio(audio: Audio) = viewModelScope.launch { repository.addAudio(audio = audio) }

    fun addAlbum(album: Album) = viewModelScope.launch { repository.addAlbum(album = album)}

    fun addCollectionName(collectionName: CollectionName) = viewModelScope.launch { repository.addCollectionName(collectionName = collectionName) }
    }