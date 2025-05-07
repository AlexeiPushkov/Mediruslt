package com.diplom.mediresult

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.diplom.mediresult.presentation.auth.SupabaseAuthViewModel
import com.diplom.mediresult.presentation.nvgraph.NavigationGraph
import com.diplom.mediresult.presentation.nvgraph.Route
import com.diplom.mediresult.ui.theme.MediresultTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MediresultTheme {
                val navigationController = rememberNavController()

                val context = LocalContext.current

                val viewModel: SupabaseAuthViewModel = viewModel()
                if(viewModel.isUserLoggedIn(context = context)){
                    NavigationGraph(
                        navController = navigationController,
                        startDestination = Route.MainScreen.route
                    )
                }
                else{
                    NavigationGraph(
                        navController = navigationController,
                        startDestination = Route.OnBoardingScreen.route
                    )
                }
            }
        }
    }
}

