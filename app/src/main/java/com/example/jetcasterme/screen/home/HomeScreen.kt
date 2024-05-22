package com.example.jetcasterme.screen.home

import android.view.WindowInsets
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetcasterme.R
import com.example.jetcasterme.components.JetcasterImage
import com.example.jetcasterme.components.JetcasterTopAppBar
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName
import com.example.jetcasterme.model.PlayAudio
import com.example.jetcasterme.ui.theme.JetcasterMeTheme

private val HeaderHeight = 120.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,
               modifier: Modifier = Modifier,
               viewmodel: HomeScreenViewmodel = hiltViewModel()) {
    Box(modifier = modifier.fillMaxSize()){
        val scroll = rememberScrollState(0)

        JetcasterTopAppBar(height = HeaderHeight)


    }







}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplaySelectedAlbums(
    modifier:Modifier = Modifier,
    albums: List<Album>){
    val pagerState = rememberPagerState(pageCount = {albums.size})
    if (albums.isEmpty()) Box{}
    HorizontalPager(state = pagerState) {page->

        Box(modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(color = Color.Black),
            contentAlignment = Alignment.Center,
        ){
            JetcasterImage(imageUrl = albums[page].albumImage,
                description = "${albums[page].albumName} image",
                size = 120.dp)
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsRow(
    tabState: MutableState<Int> = remember { mutableIntStateOf(0) },

){
    //var state by remember { mutableStateOf(0) }
    val titles = listOf(stringResource(id = R.string.tab0),
        stringResource(id = R.string.tab1))
    PrimaryTabRow(
        selectedTabIndex = tabState.value,
        containerColor = Color.Black,
        contentColor = Color.White.copy(0.7f),
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                color = Color.White,
                width = 140.dp
            )
        }

    ){
        titles.forEachIndexed { index, title ->
            Tab(
                selected = tabState.value == index,
                onClick = { tabState.value = index },
                text = { Text(text = title, style = MaterialTheme.typography.titleMedium) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.7f),
            )
        }

    }
}
@Composable
fun CollectionFilterItem(
    modifier: Modifier = Modifier,
    collectionName: CollectionName,
    isSelected : MutableState<Boolean> = remember {
        mutableStateOf(false)
    },
    onSelected:(String)->Unit){
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                color = if (!isSelected.value) Color.White.copy(0.1f) else MaterialTheme.colorScheme.primary.copy(
                    0.1f
                )
            )
            .clickable {
                isSelected.value = !isSelected.value
                onSelected(collectionName.collectionName)
            }
    ) {
        Text(text = collectionName.collectionName,
            color = if (!isSelected.value) Color.White.copy(0.7f) else MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall,
            modifier = modifier.padding(horizontal = 12.dp,vertical = 6.dp))
    }
}

@Composable
fun AlbumImageAndName(
    modifier: Modifier = Modifier,
    album: Album,
    isAdded:MutableState<Boolean> = remember {
        mutableStateOf(true)
    },
    onRemove:(Album)->Unit,
    onAdd:(Album)->Unit){
    Column(modifier = modifier.width(100.dp)) {
        Box(modifier = modifier){
            JetcasterImage(imageUrl = album.albumImage,
                description = "${album.albumName} image",
                size = 100.dp)

            IconButton(onClick = { isAdded.value = !isAdded.value
                                 if (!isAdded.value) onAdd(album) else onRemove(album)},
                modifier = modifier.align(Alignment.BottomEnd)) {
                if (!isAdded.value){
                    Box(modifier = modifier
                        .clip(CircleShape)
                        .size(30.dp)
                        .background(color = Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_album_to_library),
                            tint = Color.Black.copy(0.6f))
                    }
                }else{
                    Box(modifier = modifier
                        .clip(CircleShape)
                        .size(30.dp)
                        .background(color = Color.Black.copy(0.4f), shape = CircleShape),
                        contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Default.Done,
                            contentDescription = stringResource(id = R.string.add_album_to_library),
                            tint = Color.White,
                            modifier = modifier.size(25.dp))
                    }
                }

            }
        }
        Text(text = album.albumName,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier.padding(top = 4.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis)
    }
}
@Composable
fun PlayAudioList(playAudioList: List<PlayAudio>){
    LazyColumn {

    }
}

@Composable
fun PlayAudioItem(audio: PlayAudio,
              modifier: Modifier = Modifier){
    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(horizontal = 16.dp)) {
        val (title,album,playButton,time,image,addToList,moreInfo) = createRefs()

        //title
        Text(text = "${audio.audioCount}:  ${audio.audioName}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = modifier
                .width(250.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 8.dp)
                },
            fontWeight = FontWeight.Bold
        )

        //album
        Text(text = audio.albumName,
            modifier = modifier.constrainAs(album){
                top.linkTo(title.bottom,margin = 4.dp)
            },
            color = Color.White.copy(0.7f),
            style = MaterialTheme.typography.bodyMedium)

        //play button
        IconButton(onClick = { /*TODO*/ },
            modifier = modifier.constrainAs(playButton){
                bottom.linkTo(parent.bottom,margin=8.dp)
            }) {
            Box(modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color = Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center) {
                Icon(imageVector = Icons.Default.PlayArrow,
                    contentDescription = stringResource(id = R.string.play_icon),
                    tint = Color.Black)
            }

        }

        //time
        Text(text = "${audio.releaseTime}·${audio.playLength} mins",
            color = Color.White.copy(0.7f),
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier.constrainAs(time){
                start.linkTo(playButton.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 25.dp)
            })

        //image
        JetcasterImage(
            imageUrl = audio.albumImage,
            description = "${audio.albumName} image",
            size = 65.dp,
            modifier = modifier.constrainAs(image) {
                top.linkTo(parent.top, margin = 12.dp)
                end.linkTo(parent.end, margin = 8.dp)
            })
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(audio.albumImage)
//                .crossfade(true)
//                .build(),
//            contentDescription = "${audio.albumName} image",
//            placeholder = painterResource(id = R.drawable.placeholder_image),
//            modifier = modifier
//                .size(65.dp)
//                //.wrapContentSize()
//                .clip(MaterialTheme.shapes.medium)
//                .constrainAs(image) {
//                    top.linkTo(parent.top, margin = 12.dp)
//                    end.linkTo(parent.end, margin = 8.dp)
//                },
//            contentScale = ContentScale.Crop)

        //moreInfo
        IconButton(onClick = { /*TODO*/ },
            modifier = modifier.constrainAs(moreInfo){
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
            }) {
            Icon(imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more_vert_icon),
                tint = Color.White.copy(0.7f))
        }

        //addToList
        IconButton(onClick = { /*TODO*/ },
            modifier = modifier.constrainAs(addToList){
                bottom.linkTo(parent.bottom,margin = 8.dp)
                end.linkTo(moreInfo.start)
            }) {
            Icon(painter = painterResource(id = R.drawable.ic_add_playlist),
                contentDescription = stringResource(id =R.string.add_playlist_icon ),
                tint = Color.White.copy(0.7f))
        }




    }
}
@Preview
@Composable
private fun SelectedAlbumsDisplayPreview(){
    DisplaySelectedAlbums(
        albums = fakeDataList)
}

@Preview
@Composable
private fun TabsRowPreview(){
    JetcasterMeTheme {
        TabsRow()
    }

}
@Preview
@Composable
private fun CollectionFilterItemPreview(){
    JetcasterMeTheme {
        CollectionFilterItem(
            collectionName = CollectionName(
                uid = 1,
                collectionName = "Society & Culture"
            ),
            onSelected = {""}
        )
    }

}

@Preview
@Composable
private fun AlbumImageAndNamePreview(){
    AlbumImageAndName(album =
    Album(uid = 1, albumImage = "",
        albumName = "No Stupid Questions",
        collectionName = "Society & Culture"),
        onAdd = { fakeDataList[0]},
        onRemove = { fakeDataList[0]},
        )

}

@Preview
@Composable
private fun AudioItemPreview(){
    PlayAudioItem(
        PlayAudio( audioCount = 196, audioName = "What's Wrong With Being a Little Neurotic?",
            albumName = "No Stupid Life",releaseTime = "May 19,2024", playLength = 30,
            albumImage ="https://cdn.pixabay.com/photo/2023/06/16/15/14/sunset-8068208_1280.jpg" )
    )
}

val fakeDataList = listOf(
    Album(uid = 1, albumImage = "",
    albumName = "No Stupid Questions",
    collectionName = "Society & Culture"),
    Album(uid = 1, albumImage = "",
        albumName = "No Stupid Questions",
        collectionName = "Society & Culture")
    )
