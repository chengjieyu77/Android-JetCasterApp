package com.example.jetcasterme.screen.home

import android.provider.MediaStore.Audio.Artists.Albums
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName
import com.example.jetcasterme.model.PlayAudio
import com.example.jetcasterme.repository.JetcasterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"

@HiltViewModel
class HomeScreenViewmodel @Inject constructor(private val repository: JetcasterRepository):ViewModel(){
    private val _tabState = mutableIntStateOf(1)
    private val _selectedAlbums = mutableStateListOf<Album>()
    private val _isCollectionNameSelected = mutableStateOf<Boolean>(false)
    private val _collectionNameResponse = mutableStateOf<String?>("Society & Culture")
    private val _collectionNameList = MutableStateFlow<List<CollectionName>>(emptyList())
    private val _albumsListFilteredByCollectionName = MutableStateFlow<List<Album>>(emptyList())
    private val _allPlayAudios = MutableStateFlow<List<PlayAudio>>(emptyList())
    private val _playAudiosFilteredByCollectionName = mutableStateListOf<PlayAudio>()
    private val _playAudiosFilteredByAlbum = MutableStateFlow<List<PlayAudio>>(emptyList())

    val tabState
        get() = _tabState
    val selectedAlbums:List<Album>
        get() = _selectedAlbums
    val isCollectionNameSelected
        get() = _isCollectionNameSelected

    val collectionNameResponse
        get() = _collectionNameResponse

    val collectionNameList = _collectionNameList.asStateFlow()
    val albumsListFilteredByCollectionName = _albumsListFilteredByCollectionName.asStateFlow()

    val allPlayAudios
        get() = _allPlayAudios

    val playAudioFilteredByCollectionName:List<PlayAudio>
        get() = _playAudiosFilteredByCollectionName

    init {
        viewModelScope.launch {
            repository.getAllCollectionNames().collect{listOfCollectionNames->
                if (listOfCollectionNames.isEmpty()){
                    Log.d(TAG,"empty collection list")
                }else{
                    _collectionNameList.value = listOfCollectionNames
                }
            }

        }
        viewModelScope.launch {
            repository.getAlbumsByCollection(_collectionNameResponse.value!!).collect{listOfAlbums->
                if (listOfAlbums.isEmpty()){
                    Log.d(TAG,"empty album list")
                }else{
                    _albumsListFilteredByCollectionName.value = listOfAlbums
                }
            }

        }
        viewModelScope.launch {
            repository.getAllPlayAudios().collect{listOfPlayAudios->
                if (listOfPlayAudios.isEmpty()){
                    Log.d(TAG,"empty audio list")
                }else{
                    _allPlayAudios.value = listOfPlayAudios
                }
            }
        }




    }

    fun onCollectionNameResponse(collectionName:String?){
        _collectionNameResponse.value = collectionName
        _playAudiosFilteredByCollectionName.clear()
        loadAlbumsByCollectionName(collectionName = collectionName!!)
        loadPlayAudiosByCollectionName(collectionName = collectionName)
    }

    fun onAlbumResponse(selected:Boolean,album: Album){
        if (selected){
            _selectedAlbums.add(album)
        }else{
            _selectedAlbums.remove(album)
        }
    }

    private fun loadAlbumsByCollectionName(collectionName: String) = viewModelScope.launch{
            repository.getAlbumsByCollection(collectionName = collectionName).collect{listOfAlbums->
                _albumsListFilteredByCollectionName.value =listOfAlbums
            }
    }

    fun loadAllPlayAudios() = viewModelScope.launch {
        repository.getAllPlayAudios().collect{listOfPlayAudios->
            _allPlayAudios.value = listOfPlayAudios
        }
    }

    fun loadPlayAudiosByCollectionName(collectionName: String) = viewModelScope.launch {
        _albumsListFilteredByCollectionName.value.forEach { album ->
            getPlayAudiosByAlbum(album.albumName!!)
            _playAudiosFilteredByCollectionName.addAll(_playAudiosFilteredByAlbum.value)

        }
    }

    fun getPlayAudiosByAlbum(albumName:String) = viewModelScope.launch {
        repository.getPlayAudiosByAlbum(albumName).collect{listOfPlayAudio->
            _playAudiosFilteredByAlbum.value = listOfPlayAudio
        }
    }






}