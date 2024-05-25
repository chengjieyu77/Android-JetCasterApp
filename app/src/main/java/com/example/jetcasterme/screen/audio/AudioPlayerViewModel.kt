package com.example.jetcasterme.screen.audio

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Audio
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class AudioPlayerViewModel :ViewModel(){
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
    fun AudioViewBuilder(context: Context): PlayerView{
        val activity = context as Activity
        val playerView = PlayerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                100
            )
            player = exoPlayer
            useController = true
        }
        return playerView
    }
}

