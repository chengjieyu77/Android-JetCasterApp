package com.example.jetcasterme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetcasterme.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetcasterTopAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color =  Color(0xFF1D1913),
    height: Dp = 120.dp,
    onNavToProfile:()->Unit = {}
) {
    Box {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(color = containerColor))
        TopAppBar(title = { JetcasterIcon() },
            modifier = modifier,
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "search icon",
                        tint = Color.LightGray.copy(0.6f))
                }
                IconButton(onClick =  onNavToProfile ) {
                    Icon(imageVector = Icons.Default.AccountCircle,
                        contentDescription = "account icon",
                        tint = Color.LightGray.copy(0.6f))
                }

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ))
    }








}

@Composable
fun JetcasterIcon(modifier: Modifier = Modifier){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "icon logo",
            tint = Color.Unspecified)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(painter = painterResource(id = R.drawable.ic_text_logo),
            contentDescription = "icon logo",
            modifier = Modifier
                .width(120.dp)
                .height(48.dp),
            tint = Color.White,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1D1913)
@Composable
fun JetcasterTopAppBarPreview(){
    JetcasterTopAppBar()
}