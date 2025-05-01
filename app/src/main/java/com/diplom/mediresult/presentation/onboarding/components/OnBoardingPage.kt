package com.diplom.mediresult.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplom.mediresult.R
import com.diplom.mediresult.ui.theme.MediresultTheme

@Composable
fun OnBoardingPage(
    page: Page
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = page.title,
            color = colorResource(R.color.green),
            fontSize = 20.sp,
        )
        Text(
            text = page.description
        )
        Spacer(
            modifier = Modifier.height(20.dp),
        )
        Image(
            modifier = Modifier.size(size = 300.dp),
            painter = painterResource(page.image),
            contentDescription = "Изображение",
        )
    }

}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagePreview(){
    MediresultTheme {
        OnBoardingPage(
            page = pages[0]
        )
    }
}