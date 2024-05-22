package com.example.jetcasterme.screen.home

import android.provider.MediaStore.Audio.Artists.Albums
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.repository.JetcasterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewmodel @Inject constructor(private val repository: JetcasterRepository):ViewModel(){
    private val _selectedAlbums = mutableStateListOf<Album>()

    val selectedAlbums
        get() = _selectedAlbums

    fun addToSelectedAlbums(album:Album){
        _selectedAlbums.add(album)
    }

    fun removeFromSelectedAlbums(album: Album){
        _selectedAlbums.remove(album)
    }

}