package com.example.jetcasterme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetcasterme.screen.AudioScreen
import com.example.jetcasterme.screen.database_operation.DatabaseOperationScreen
import com.example.jetcasterme.screen.home.HomeScreen

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
            "${JetcasterMeScreens.AudioScreen.name}/{snackName}",
            arguments = listOf(navArgument("snackName"){type = NavType.StringType})
        ){navBack->
            navBack.arguments?.getString("snackName").let{snackName->
                AudioScreen(navController = navController,snackName = snackName.toString())
            }

        }
        composable(JetcasterMeScreens.DatabaseOperationScreen.name){
            DatabaseOperationScreen(navController = navController)
        }
    }
}