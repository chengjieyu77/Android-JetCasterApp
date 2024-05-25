package com.example.jetcasterme.screen.audio

import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetcasterme.R
import com.example.jetcasterme.components.JetcasterImage
import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.PlayAudio
import com.example.jetcasterme.ui.theme.JetcasterMeTheme

private const val TAG = "AudioScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioScreen(navController: NavHostController,
                modifier: Modifier = Modifier,
                audioName:String?,
                viewModel: AudioScreenViewModel = hiltViewModel()) {
    //viewModel.getPlayAudioByName(audioName)
    Log.d(TAG, viewModel.currentPlayAudio.toString())
    //val mContext = LocalContext.current
    //val mMediaPlayer = MediaPlayer.create(mContext,R.raw.demo_test_music)
    //val mMediaPlayer = MediaPlayer.create(mContext, Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"))

    //val mMediaPlayer = MediaPlayer.create(mContext,Uri.parse(viewModel.currentPlayAudio!!.url))
//    val isPlaying = remember{
//            mutableStateOf(mMediaPlayer.isPlaying)}
    val scope = rememberCoroutineScope()
    Log.d(TAG,viewModel.currentPlayAudioName)
    Log.d(TAG,"isNameGot" + viewModel.isNameGot.toString())
    Log.d(TAG,"argument"+viewModel.argument)

    LaunchedEffect(key1 = viewModel.currentPlayAudio) {
        viewModel.setCurrentPlayAudioName(audioName!!)
        viewModel.getPlayAudioByName(audioName)
        //viewModel.initializeMediaPlayer(context = mContext,viewModel.currentPlayAudio?.url!!)
    }


    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ) {
        if (viewModel.currentPlayAudio == null){
            Text(text = "No such audio!!")
        }else{
            val mContext = LocalContext.current
            //val mMediaPlayer = MediaPlayer.create(mContext,Uri.parse(viewModel.currentPlayAudio!!.url))
            val mMediaPlayer = MediaPlayer.create(mContext,R.raw.demo_test_music)
            val playing = remember{
            mutableStateOf(mMediaPlayer.isPlaying)}
            val position = remember {
                mutableFloatStateOf(0f)
            }
            //viewModel.initializeMediaPlayer(context = mContext,viewModel.currentPlayAudio?.url!!)
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AudioTopBar(
                    onNavClick ={
                        mMediaPlayer.stop()
                        mMediaPlayer.release()
                        navController.popBackStack()
                    //viewModel.releaseMediaPlayer()

                    }
                )
                JetcasterImage(imageUrl = viewModel.currentPlayAudio!!.albumImage,
                    description = viewModel.currentPlayAudio!!.audioName,
                    size = 300.dp,
                    modifier = modifier.padding(32.dp)
                )
                AudioNameAndAlbumRow(audio = viewModel.currentPlayAudio!!)


                MediaPlayerSection(
                    isPlaying = playing.value,
                    playLength = viewModel.currentPlayAudio!!.playLength,
                    startAndPause = { if (playing.value){mMediaPlayer.pause()} else mMediaPlayer.start() },
                    sliderPosition = position){
                    mMediaPlayer.seekTo(it.toInt())
                }

                object :CountDownTimer(mMediaPlayer.duration.toLong(),100){
                    override fun onTick(millisUntilFinished: Long) {
                        position.value = mMediaPlayer.currentPosition.toFloat()
                        if (mMediaPlayer.currentPosition == mMediaPlayer.duration){
                            playing.value = false
                        }
                    }

                    override fun onFinish() {

                    }
                }.start()



            }
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPlayerSection(
    modifier: Modifier = Modifier,
    isPlaying:Boolean?,
    playLength:Int,
    startAndPause:()->Unit = {},
    stop:()->Unit = {},
    sliderPosition:MutableState<Float>,
    playerSeekTo:(Float)->Unit

){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderPosition.value,
            onValueChange = {
                sliderPosition.value = it
                playerSeekTo(it) },
            valueRange = 0f..playLength.toFloat(),

        )
        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "0s", style = MaterialTheme.typography.labelMedium, color = Color.White)
            Text(text = "${playLength.times(60)}s",style = MaterialTheme.typography.labelMedium, color = Color.White)
        }
        Row(
            modifier = modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            IconButton(onClick = {} ) {
                Icon(painter = painterResource(id = R.drawable.ic_skip_previous),
                    contentDescription = "", Modifier.size(100.dp),
                    tint = Color.White)
            }
            IconButton(onClick = {} ) {
                Icon(painter = painterResource(id = R.drawable.ic_replay_10),
                    contentDescription = "", Modifier.size(100.dp),
                    tint = Color.White)
            }

                // IconButton for Pause Action
            IconButton(onClick =startAndPause,
                    modifier = modifier.size(60.dp)) {
                    Icon(
                        painterResource(id = if (isPlaying == true) R.drawable.ic_stop_circle else R.drawable.ic_play_circle ),
                        contentDescription = "", Modifier.size(100.dp),
                        tint = Color.White)
                }



            IconButton(onClick = {  }) {
                Icon(painter = painterResource(id = R.drawable.ic_forward_10),
                    contentDescription = "", Modifier.size(100.dp),
                    tint = Color.White)
            }
            IconButton(onClick = {  }) {
                Icon(painter = painterResource(id = R.drawable.ic_skip_next),
                    contentDescription = "", Modifier.size(100.dp),
                    tint = Color.White)
            }

        }

    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AudioNameAndAlbumRow(
    modifier: Modifier = Modifier,
    audio:PlayAudio,

){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = audio.audioName,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier
                .basicMarquee()
                .padding(bottom = 4.dp))
        Text(text = audio.albumName,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(0.7f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioTopBar(
    onNavClick:()->Unit = {},
    onAddToListClick:()->Unit = {},
    onMoreClick:()->Unit = {},
){
    TopAppBar(title = { },
        navigationIcon = {
            IconButton(onClick = onNavClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_icon),
                    tint = Color.White.copy(0.8f)
                )
            }
        },
        actions = {
            IconButton(onClick = onAddToListClick) {
                Icon(painterResource(id = R.drawable.ic_add_playlist),
                    contentDescription = stringResource(id = R.string.add_playlist_icon),
                    tint = Color.White.copy(0.8f))
            }
            IconButton(onClick = onMoreClick) {
                Icon(imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.more_vert_icon),
                    tint = Color.White.copy(0.8f))
            }
        })
}
@Preview
@Composable
fun AudioPlaySectionPreview(){
    JetcasterMeTheme {
        MediaPlayerSection(isPlaying = true, playLength = 2, sliderPosition = remember {
            mutableStateOf(0f)
        }){

        }
    }

}

@Preview
@Composable
fun AudioNameAndAlbumRowPreview(){
    JetcasterMeTheme {
        AudioNameAndAlbumRow(audio =
        PlayAudio( audioCount = 830,
            audioName = "What's Wrong With Being a Little Neurotic",
            albumName = "No Stupid Questions",
            releaseTime = "May 12,2024",
            playLength = 38,
            albumImage = "",
            id=1,
            url = ""
            )
        )
    }
}
@Preview
@Composable
fun AudioTopBarPreview(){
    JetcasterMeTheme {
        AudioTopBar()
    }

}