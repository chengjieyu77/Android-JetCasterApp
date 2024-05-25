package com.example.jetcasterme.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetcasterme.screen.audio.AudioScreen
import com.example.jetcasterme.screen.audio.AudioScreenViewModel
import com.example.jetcasterme.screen.database_operation.DatabaseOperationScreen
import com.example.jetcasterme.screen.home.HomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun JetcasterMeNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = JetcasterMeScreens.HomeScreen.name){

        composable(JetcasterMeScreens.HomeScreen.name){
            HomeScreen(navController = navController){audioName->
                navController.navigate(JetcasterMeScreens.AudioScreen.name + "/$audioName")
            }
        }

        composable(
            "${JetcasterMeScreens.AudioScreen.name}/{audioName}",
            arguments = listOf(navArgument("audioName"){type = NavType.StringType})
        ){navBack->
            navBack.arguments?.getString("audioName").let{audioName->
                val viewModel = hiltViewModel<AudioScreenViewModel>()
                AudioScreen(navController = navController,audioName = audioName.toString())
            }

        }
        composable(JetcasterMeScreens.DatabaseOperationScreen.name){
            DatabaseOperationScreen(navController = navController)
        }
    }
}