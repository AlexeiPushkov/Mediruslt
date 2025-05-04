package com.diplom.mediresult.presentation.nvgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.diplom.mediresult.presentation.CheckCodeScreen
import com.diplom.mediresult.presentation.onboarding.OnBoardingScreen
import com.diplom.mediresult.presentation.auth.LoginScreen
import com.diplom.mediresult.presentation.MainScreen
import com.diplom.mediresult.presentation.auth.SignInScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Route.OnBoardingScreen.route
    ){
        composable(Route.OnBoardingScreen.route){
            OnBoardingScreen(
                navController = navController
            )
        }
        composable(Route.LoginScreen.route) {
            LoginScreen(
                navController = navController
            )
        }
        composable(Route.SinUpScreen.route) {
            SignInScreen(
                navController = navController
            )
        }

        composable(Route.CheckCodeScreen.route) {
            CheckCodeScreen()
        }
        composable(Route.MainScreen.route) {
            MainScreen()
        }

    }
}