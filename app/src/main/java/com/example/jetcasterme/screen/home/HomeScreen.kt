package com.example.jetcasterme.screen.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.jetcasterme.R
import com.example.jetcasterme.components.JetcasterImage
import com.example.jetcasterme.components.JetcasterTopAppBar
import com.example.jetcasterme.data.JetcasterDatabaseDao
import com.example.jetcasterme.model.Album
import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName
import com.example.jetcasterme.model.PlayAudio
import com.example.jetcasterme.navigation.JetcasterMeScreens
import com.example.jetcasterme.screen.audio.AudioScreenViewModel

import com.example.jetcasterme.ui.theme.JetcasterMeTheme
import kotlinx.coroutines.flow.forEach
import kotlin.reflect.KProperty0

private val HeaderHeight = 120.dp
private val SelectedAlbumHeight = 220.dp
private val TabsRowHeight = 60.dp
private val AlbumImageAndNameHeight = 128.dp
private val TabsBackgoundHeight = AlbumImageAndNameHeight + TabsRowHeight + HeaderHeight
private val MaxTabOffset = SelectedAlbumHeight

private const val  TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,
               modifier: Modifier = Modifier,
               viewmodel: HomeScreenViewmodel = hiltViewModel(),
               //audioviewModel: AudioScreenViewModel = hiltViewModel(),
               onClickToAudioDetail: (String) -> Unit) {
    val tabState = rememberSaveable{
        mutableIntStateOf(1)
    }
    val collectionList = viewmodel.collectionNameList.collectAsState().value
    val albumsList = viewmodel.albumsListFilteredByCollectionName.collectAsState().value
    val allPlayAudiosList = viewmodel.allPlayAudios.collectAsState().value
    //val displayedPlayAudiosList = viewmodel.playAudioFilteredByCollectionName.toList()
    val selectedAlbums = viewmodel.selectedAlbums
    val playAudiosInYourLibrary = viewmodel.playAudioInYourLibrary
    Log.d(TAG,albumsList.toString())
    Log.d(TAG,allPlayAudiosList.toString())
    //Log.d(TAG,displayedPlayAudiosList.toString())
    Log.d(TAG, "playAudiosInYourLibrary"+playAudiosInYourLibrary.toString())



    Box(modifier = modifier
        .fillMaxSize()
        .background(color = Color.Black)){
        val scroll = rememberScrollState(0)
        JetcasterTopAppBar(height = HeaderHeight,modifier = modifier
            .align(Alignment.TopCenter)
            .zIndex(99f)){
                navController.navigate(JetcasterMeScreens.DatabaseOperationScreen.name)
            }
//            Body(
//                scroll = scroll,
//                selectedAlbums = viewmodel.selectedAlbums,
//                tabState = tabState,
//                onAlbumSelected = viewmodel::onAlbumResponse,
//                selectedCollectionName = viewmodel.collectionNameResponse.value,
//                onCollectionNameSelected = viewmodel::onCollectionNameResponse,
//            ) {
//
//            }
        val minTabOffset = with(LocalDensity.current){-20.dp.toPx()}
        val maxTabOffset = with(LocalDensity.current){0.dp.toPx()}
            Column {
                    Spacer(modifier = modifier
                        .height(HeaderHeight)
                        .statusBarsPadding()
                        .fillMaxWidth())
                Column(modifier =modifier.verticalScroll(scroll)) {
                    DisplaySelectedAlbums(
                        albums = viewmodel.selectedAlbums,
                        onAlbumSelected= viewmodel::onAlbumResponse)

                        TabsRow(tabState = viewmodel.tabState,
                            isAlbumEmpty = viewmodel.selectedAlbums.isEmpty()){scroll.value}

                        when(viewmodel.tabState.intValue){
                            0 -> Column {
                                viewmodel.playAudioInYourLibrary.forEach { playAudio ->
                                    PlayAudioItem(audio = playAudio){audioName->
                                        navController.navigate(JetcasterMeScreens.AudioScreen.name+"/$audioName")
                                        //audioviewModel.setCurrentPlayAudioName(audioName)
                                    }
                                    HorizontalDivider()
                                }
                            }
                            1 -> Column(modifier = modifier
                                .padding(horizontal = 16.dp)
                                //.verticalScroll(scroll)
                            ) {
                                Spacer(modifier = modifier.height(16.dp))
                                CollectionFilterRow(collectionList = collectionList,
                                    selectedCollectionName = viewmodel.collectionNameResponse.value,
                                    onCollectionNameSelected = viewmodel::onCollectionNameResponse)
                                Spacer(modifier = modifier.height(16.dp))
                                AlbumImageAndNameRow(
                                    albumList = albumsList,
                                    selectedAlbums =viewmodel.selectedAlbums,
                                    onAlbumSelected = viewmodel::onAlbumResponse)
                                Spacer(modifier = modifier.height(16.dp))
                                HorizontalDivider()

                                allPlayAudiosList.forEach {playAudio ->
                                    PlayAudioItem(audio = playAudio){audioName->
                                        navController.navigate(JetcasterMeScreens.AudioScreen.name+"/$audioName")
                                        //audioviewModel.setCurrentPlayAudioName(audioName)

                                    }
                                    HorizontalDivider()
                                }

                                //test the column scrolling
                                Spacer(modifier = modifier.height(400.dp))

                            }


                        }




                    }


                }






                }
            }























@Composable
fun Body(scroll:ScrollState,
         selectedAlbums: List<Album>,
         tabState: MutableState<Int>,
         collectionList: List<CollectionName> = emptyList(),
         selectedAlbumList: List<Album> = emptyList(),
         onAlbumSelected: (Boolean,Album) -> Unit,
         selectedCollectionName: String?,
         onCollectionNameSelected:(String?)->Unit,
         audiosInYourLibrary:List<PlayAudio> = emptyList(),
         audiosInDiscover:List<PlayAudio> = emptyList(),
         onClickToAudioDetail:(String)->Unit){
    Column {
        //DisplaySelectedAlbums(albums = selectedAlbums)
        //TabsRow(tabState = tabState)

        ContentUnderDiscover(
                selectedAlbums = selectedAlbumList,
                onAlbumSelected = onAlbumSelected,
                selectedCollectionName = selectedCollectionName,
                onCollectionNameSelected = onCollectionNameSelected
        )

        when(tabState.value){
            0 -> Text(text = "0")
            1 -> ContentUnderDiscover(
                selectedAlbums = selectedAlbumList,
                onAlbumSelected = onAlbumSelected,
                selectedCollectionName = selectedCollectionName,
                onCollectionNameSelected = onCollectionNameSelected
            )
        }




    }
}
@Composable
fun ContentUnderDiscover(
    modifier: Modifier = Modifier,
    scroll: ScrollState = rememberScrollState(),
    collectionList: List<CollectionName> = emptyList(),
    albumList: List<Album> = emptyList(),
    selectedAlbums: List<Album>,
    onAlbumSelected: (Boolean, Album) -> Unit,
    audiosInDiscover: List<PlayAudio> = emptyList(),
    selectedCollectionName: String?,
    onCollectionNameSelected:(String?)->Unit,
){
    Column(modifier = modifier.fillMaxWidth()) {
        CollectionFilterRow(collectionList = collectionList,
            selectedCollectionName = selectedCollectionName,
            onCollectionNameSelected = onCollectionNameSelected)
        Spacer(modifier = modifier.padding(32.dp))
        AlbumImageAndNameRow(albumList = albumList,
            selectedAlbums = selectedAlbums,
            onAlbumSelected = onAlbumSelected)


     }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplaySelectedAlbums(
    modifier:Modifier = Modifier,
    albums: List<Album>,
    onAlbumSelected: (Boolean,Album) -> Unit){
    val pagerState = rememberPagerState(pageCount = {albums.size})
    if (albums.isEmpty()) Box{}
    HorizontalPager(state = pagerState) {page->
        val selected = albums.contains(albums[page])
        val album = albums[page]
        Box(modifier = modifier
            .fillMaxWidth()
            .height(SelectedAlbumHeight)
            .background(color = Color.Black),
            contentAlignment = Alignment.Center,
        ){
            Column {
                AlbumImageWithIcon(albumUrl = albums[page].albumImage,
                    albumName =albums[page].albumName , isAdded = selected,
                    onAlbumSelected = {onAlbumSelected(!selected,album)})
                Text(text = "Updated a while ago",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(0.7f),
                    modifier = modifier.padding(top = 8.dp))
            }


//            JetcasterImage(imageUrl = albums[page].albumImage,
//                description = "${albums[page].albumName} image",
//                size = 120.dp)
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsRow(
    modifier: Modifier = Modifier,
    tabState: MutableState<Int>,
    isAlbumEmpty:Boolean = true,
    scrollProvider:()->Int

){
    //var state by remember { mutableStateOf(0) }

    val minTabOffset = with(LocalDensity.current){100.dp.toPx()}
    val maxTabOffset = with(LocalDensity.current){ 150.dp.toPx()}

    val titles = listOf(stringResource(id = R.string.tab0),
        stringResource(id = R.string.tab1))
    val scroll = scrollProvider()
    val newOffset = if (scroll > -250) 0 else scroll
    val newOffsetState = remember {
        mutableIntStateOf(newOffset)
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = 60.dp)
            .fillMaxWidth()
            //.statusBarsPadding()
            .offset {

                val offset =
                    if (isAlbumEmpty) scroll else newOffsetState.value//(0).coerceAtMost(scroll)// (scroll - ).coerceAtLeast(minTabOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Color.Black),
    ) {
        PrimaryTabRow(
            selectedTabIndex = tabState.value,

            //.height(TabsRowHeight),
            containerColor = Color.Black,
            contentColor = Color.White.copy(0.7f),
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        selectedTabIndex = tabState.value,
                        matchContentSize = true
                    ),
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

}

@Composable
fun CollectionFilterRow(
    modifier: Modifier = Modifier,
    collectionList: List<CollectionName>,
    selectedCollectionName:String? = "",
    onCollectionNameSelected: (String?)->Unit,
){

    LazyRow(
        modifier = modifier
            .selectableGroup()
    ) {
        items(collectionList){collection->
            val selected = collection.collectionName.equals(selectedCollectionName)
            CollectionFilterItem(collectionName = collection,
                isSelected = selected,
                onSelected = {onCollectionNameSelected(collection.collectionName)})
            Spacer(modifier = modifier.width(8.dp))

        }
    }
}
@Composable
fun CollectionFilterItem(
    modifier: Modifier = Modifier,
    collectionName: CollectionName,
    isSelected : Boolean,
    onSelected:(String?)->Unit){
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .selectable(
                selected = isSelected,
                onClick = { onSelected(collectionName.collectionName) }

            )
            .background(
                color = if (!isSelected) Color.White.copy(0.1f) else MaterialTheme.colorScheme.primary.copy(
                    0.1f
                )
            )

//            .clickable {
//                isSelected.value = !isSelected.value
//                onSelected(collectionName.collectionName)
//            }
    ) {
        collectionName.collectionName?.let {
            Text(text = it,
                color = if (!isSelected) Color.White.copy(0.7f) else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall,
                modifier = modifier.padding(horizontal = 12.dp,vertical = 6.dp))
        }
    }
}
@Composable
fun AlbumImageAndNameRow(
    modifier: Modifier = Modifier,
    albumList: List<Album>,
    selectedAlbums: List<Album>,
    onAlbumSelected: (Boolean,Album)->Unit
){
    LazyRow(modifier = modifier) {
        items(albumList){album->
            val selected = selectedAlbums.contains(album)
            AlbumImageAndName(album = album,
                isAdded = selected,
                onAlbumSelected = {onAlbumSelected(!selected,album)})
            Spacer(modifier = modifier.width(16.dp))
        }
    }
}
@Composable
fun AlbumImageAndName(
    modifier: Modifier = Modifier,
    album: Album,
    isAdded:Boolean,
    onAlbumSelected: () -> Unit){
    Column(modifier = modifier.width(AlbumImageAndNameHeight)) {
        AlbumImageWithIcon(albumUrl = album.albumImage,
            albumName = album.albumName,
            isAdded = isAdded,
            onAlbumSelected = onAlbumSelected)
        album.albumName?.let {
            Text(text = it,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                modifier = modifier.padding(top = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }
    }
}
@Composable
fun AlbumImageWithIcon(
    modifier: Modifier = Modifier,
    albumUrl:String?,
    albumName:String?,
    isAdded:Boolean,
    onAlbumSelected: () -> Unit

){
    Box(modifier = modifier){
        JetcasterImage(imageUrl = albumUrl,
            description = "$albumName image",
            size = AlbumImageAndNameHeight)

        IconButton(onClick = { onAlbumSelected()},
            modifier = modifier.align(Alignment.BottomEnd)) {
            if (!isAdded){
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
}
@Composable
fun PlayAudioList(playAudioList: List<PlayAudio>){
    LazyColumn {

    }
}

@Composable
fun PlayAudioItem(audio: PlayAudio,
                  modifier: Modifier = Modifier,
                  onClickToAudioDetail: (String) -> Unit){
    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(horizontal = 16.dp)
        .clickable { onClickToAudioDetail(audio.audioName) }) {
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
                .size(30.dp)
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
                bottom.linkTo(parent.bottom, margin = 23.dp)
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
                bottom.linkTo(parent.bottom, margin = 6.dp)
            }) {
            Icon(imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more_vert_icon),
                tint = Color.White.copy(0.7f))
        }

        //addToList
        IconButton(onClick = { /*TODO*/ },
            modifier = modifier.constrainAs(addToList){
                bottom.linkTo(parent.bottom,margin = 6.dp)
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
private fun CollectionNameFilterRowPreview(){
    JetcasterMeTheme {
        CollectionFilterRow(
            collectionList = fakeCollectionList,
            selectedCollectionName = "Society & Culture",
            onCollectionNameSelected = {})
    }

}

//@Preview
//@Composable
//private fun BodyPreview(){
//    Body(scroll = rememberScrollState(),
//        selectedAlbums = fakeAlbumList,
//        tabState = remember{ mutableIntStateOf(1) }) {
//
//    }
//}
//@Preview
//@Composable
//private fun SelectedAlbumsDisplayPreview(){
//    DisplaySelectedAlbums(
//        albums = fakeAlbumList){true,album->
//
//    }
//}

@Preview
@Composable
private fun TabsRowPreview(){
    JetcasterMeTheme {
        TabsRow(tabState = remember{ mutableIntStateOf(1) },
            scrollProvider = { 1 })
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
            onSelected = {""},
            isSelected = true
        )
    }

}

@Preview
@Composable
private fun AlbumImageAndNamePreview(){
    AlbumImageAndName(album =fakeAlbumList[0],
        isAdded = true,
        onAlbumSelected = {}
        )

}

@Preview
@Composable
private fun AudioItemPreview(){
    PlayAudioItem(
        PlayAudio(
            audioCount = 196, audioName = "What's Wrong With Being a Little Neurotic?",
            albumName = "No Stupid Life", releaseTime = "May 19,2024", playLength = 30,
            albumImage = "https://cdn.pixabay.com/photo/2023/06/16/15/14/sunset-8068208_1280.jpg",
            id = 1,
            url = ""
        )
    ){

    }
}


val fakeAlbumList = listOf(
    Album(uid = 1, albumImage = "",
    albumName = "No Stupid Questions",
    collectionName = "Society & Culture"),
    Album(uid = 1, albumImage = "",
        albumName = "No Stupid Questions",
        collectionName = "Society & Culture"),
    Album(uid = 1, albumImage = "",
        albumName = "No Stupid Questions",
        collectionName = "Society & Culture"),
    Album(uid = 1, albumImage = "",
        albumName = "No Stupid Questions",
        collectionName = "Society & Culture"),
    )

val fakeCollectionList = listOf(
    CollectionName(uid = 1, collectionName = "Society & Culture"),
    CollectionName(uid = 2, collectionName = "News"),
    CollectionName(uid = 3, collectionName = "Technology"),
    CollectionName(uid = 4, collectionName = "Arts"),
)

val fakeAudioList = listOf(
        Audio(uid = 1, audioCount = 196,
            audioName = "What's Wrong With Being a Little Neurotic?",
            albumName = "No Stupid Life", releaseTime = "May 19,2024",
            playLength = 30,
            mediaUrl = "",
            collectionName = "Society & Culture")
        )

