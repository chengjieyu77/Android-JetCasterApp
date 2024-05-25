package com.example.jetcasterme.screen.audio

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.PlayAudio
import com.example.jetcasterme.repository.JetcasterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject
private const val TAG = "AudioScreenViewModel"
@HiltViewModel
class AudioScreenViewModel @Inject constructor(private val repository: JetcasterRepository,
    savedStateHandle: SavedStateHandle):
    ViewModel(){
    private val _currentPlayAudio = mutableStateOf<PlayAudio?>(null)
    private val _allPlayAudios = MutableStateFlow<List<PlayAudio>>(emptyList())
    private val _currentPlayAudioName = mutableStateOf("")
    private val _argument = checkNotNull(savedStateHandle.get<String>("audioName"))


    var isNameGot = false

    val currentPlayAudio
        get() = _currentPlayAudio.value

    val currentPlayAudioName
        get() = _currentPlayAudioName.value

    val argument
        get() = _argument

    init {

        viewModelScope.launch {
            loadAllPlayAudios()
            setCurrentPlayAudioName(_currentPlayAudioName.value)
            getPlayAudioByName(_argument)
        }

    }
    fun setCurrentPlayAudioName(audioName: String){
        isNameGot = false
        _currentPlayAudioName.value = audioName
        isNameGot = true
    }
    private fun loadAllPlayAudios() = viewModelScope.launch {
        repository.getAllPlayAudios().collect{listOfPlayAudios->
            _allPlayAudios.value = listOfPlayAudios
        }
    }

    fun getPlayAudioByName(audioName:String?){
        //loadAllPlayAudios()
        val filteredPlayAudios = _allPlayAudios.value.filter { audio ->
            audio.audioName == audioName
        }
        if (filteredPlayAudios.isEmpty()){
            Log.d(TAG,"No such play audio")
        }else{
            _currentPlayAudio.value = filteredPlayAudios.first()
        }
    }

    private var _mMediaPlayer:MediaPlayer? = null
    private val _isPlaying = mutableStateOf(_mMediaPlayer?.isPlaying)

    val mMediaPlayer
        get() = _mMediaPlayer

    val isPlaying
        get() = _isPlaying
    fun initializeMediaPlayer(context: Context,audioUrl:String){
        _mMediaPlayer = MediaPlayer.create(context,Uri.parse(audioUrl))
    }

    fun releaseMediaPlayer(){
        _isPlaying.value = false
        _mMediaPlayer?.release()
        _mMediaPlayer = null
    }











    private var exoPlayer: ExoPlayer? = null
    fun initializePlayer(context: Context){
        exoPlayer = ExoPlayer.Builder(context).build()
    }
    fun releasePlayer(){
        exoPlayer?.playWhenReady = false
        exoPlayer?.release()
        exoPlayer = null
    }

    fun playAudio(){
        exoPlayer?.let { player->
            player.apply {
                stop()
                clearMediaItems()
                setMediaItem(MediaItem.fromUri(Uri.parse("")))
                playWhenReady = true
                prepare()
                play()
            }
        }
    }
    fun AudioViewBuilder(context: Context): PlayerView {
        val activity = context as Activity
        val playerView = PlayerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            player = exoPlayer
            useController = true
        }
        return playerView
    }
}