package com.diplom.mediresult.presentation.onboarding

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.diplom.mediresult.presentation.nvgraph.Route
import com.diplom.mediresult.presentation.onboarding.components.OnBoardingPage
import com.diplom.mediresult.presentation.onboarding.components.pages


@Composable
fun OnBoardingScreen(
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier.align(Alignment.Start),
            onClick = {
                navController.navigate(route = Route.LoginScreen.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Black),
        ){
            Text(
                text = "Пропустить",
                color = Color.Blue
            )
        }

        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        HorizontalPager(
            modifier = Modifier.fillMaxHeight(0.9f),
            state = pagerState
        ) { index ->
            OnBoardingPage(page = pages[index])
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.Blue else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier.height(40.dp),
        )
    }
}