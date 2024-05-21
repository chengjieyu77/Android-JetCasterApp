package com.example.jetcasterme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetcasterme.screen.AudioScreen
import com.example.jetcasterme.screen.home.HomeScreen

@Composable
fun JetcasterMeNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = JetcasterMeScreens.HomeScreen.name){
        composable(JetcasterMeScreens.HomeScreen.name){
            HomeScreen(navController = navController)
        }
        composable(JetcasterMeScreens.AudioScreen.name){
            AudioScreen(navController = navController)
        }
    }
}