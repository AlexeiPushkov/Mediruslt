package com.diplom.mediresult.presentation.formOrder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diplom.mediresult.presentation.components.CircleProgressBar
import com.diplom.mediresult.presentation.nvgraph.Route
import kotlinx.coroutines.delay

@Composable
fun OrderSuccessScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(key1 = true) {
            delay(2000)
            navController.navigate(Route.CheckScreen.route)
        }
        CircleProgressBar(
            isDisplayed = true
        )
        Text(
            text = "Производим оплату",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}