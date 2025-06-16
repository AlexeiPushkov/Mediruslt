package com.diplom.mediresult.presentation.main.pages.support

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.diplom.mediresult.presentation.nvgraph.Route

@Composable
fun SupportPage(
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        navController.navigate(Route.SupportScreen.route)
    }
}