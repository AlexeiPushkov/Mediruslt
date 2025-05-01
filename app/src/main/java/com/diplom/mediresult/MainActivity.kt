package com.diplom.mediresult

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.diplom.mediresult.presentation.onboarding.OnBoardingScreen
import com.diplom.mediresult.ui.theme.MediresultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MediresultTheme {
                OnBoardingScreen()

            }
        }
    }
}

