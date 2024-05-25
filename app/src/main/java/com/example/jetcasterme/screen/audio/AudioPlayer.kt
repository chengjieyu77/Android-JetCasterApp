package com.example.jetcasterme.screen.audio

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AudioPlayer(
    viewModel: AudioScreenViewModel,

){
    AndroidView(factory = { cont ->
        viewModel.AudioViewBuilder(cont)
    })
}