package com.example.jetcasterme.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetcasterme.R
import dagger.Component

@Composable
fun JetcasterImage(
    modifier:Modifier = Modifier,
    imageUrl:String?,
    description:String,
    size: Dp
){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "$description image",
        placeholder = painterResource(id = R.drawable.placeholder_image),
        modifier = modifier
            .size(size)
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
//            .constrainAs(image) {
//                top.linkTo(parent.top, margin = 8.dp)
//                end.linkTo(parent.end, margin = 8.dp)
//            }
    )
}