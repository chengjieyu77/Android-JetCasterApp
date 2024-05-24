package com.example.jetcasterme.screen.database_operation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName

@Composable
fun DatabaseOperationScreen(navController: NavHostController,
                            modifier: Modifier = Modifier,
                            viewModel: DatabaseOperationViewModel = hiltViewModel()) {

    Surface {
        Column {
            //add audio
            Button(onClick = {
                viewModel.addAudio(
                    Audio(uid = 4, audioCount = 830,
                        audioName = "Can You Be Too Nice?",
                        albumName = "No Stupid Questions",
                        releaseTime = "May 12,2024",
                        playLength = 38)
                )
            },
                modifier = modifier.padding(top = 120.dp)) {
                Text(text = "add audio to table")
            }

            //add album
            Button(onClick = {
                viewModel.addAlbum(
                    Album(uid = 4,
                        albumImage = "https://images.unsplash.com/photo-1713364471261-e7d93cca2386?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzOHx8fGVufDB8fHx8fA%3D%3D",
                        albumName = "Plant Money",
                        collectionName = "News")
                )
            },
                modifier = modifier.padding(top = 60.dp)) {
                Text(text = "add album to table")
            }

            //add collection name
            Button(onClick = {
                viewModel.addCollectionName(
                    CollectionName(uid = 5, collectionName = "Health & Fitness")
                )
            },
                modifier = modifier.padding(top = 60.dp)) {
                Text(text = "add collection name to table")
            }
        }
    }

}