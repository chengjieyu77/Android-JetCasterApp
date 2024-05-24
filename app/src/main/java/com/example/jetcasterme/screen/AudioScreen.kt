package com.example.jetcasterme.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AudioScreen(navController: NavHostController,
                snackName:String?) {
    Text(text = snackName.toString(),modifier = Modifier.padding(100.dp))
}