package com.diplom.mediresult.presentation.nvgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.diplom.mediresult.presentation.checkEmail.CheckCodeScreen
import com.diplom.mediresult.presentation.onboarding.OnBoardingScreen
import com.diplom.mediresult.presentation.auth.login.LoginScreen
import com.diplom.mediresult.presentation.main.MainScreen
import com.diplom.mediresult.presentation.auth.signup.SignInScreen
import com.diplom.mediresult.presentation.formOrder.FormOrder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
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
            MainScreen(
                navController = navController
            )
        }
        composable(Route.FormOrder.route) {
            FormOrder(
                navController = navController
            )
        }

    }
}