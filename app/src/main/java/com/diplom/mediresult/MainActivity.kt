package com.diplom.mediresult

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.diplom.mediresult.domain.usecases.AppEntryUseCases
import com.diplom.mediresult.presentation.onboarding.OnBoardingScreen
import com.diplom.mediresult.ui.theme.MediresultTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appEntryUseCases: AppEntryUseCases
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        lifecycleScope.launch {
            appEntryUseCases.readAppEntry().collect {
                Log.d("Test",it.toString())
            }
        }
        setContent {
            MediresultTheme {
                OnBoardingScreen()

            }
        }
    }
}

