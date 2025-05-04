package com.diplom.mediresult

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.diplom.mediresult.presentation.nvgraph.NavigationGraph
import com.diplom.mediresult.ui.theme.MediresultTheme
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MediresultTheme {
                val navigationController = rememberNavController()
                NavigationGraph(navController = navigationController)
            }
        }
    }
}

