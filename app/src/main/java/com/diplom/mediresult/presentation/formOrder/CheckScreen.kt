package com.diplom.mediresult.presentation.formOrder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.presentation.nvgraph.Route
import java.time.Month

@Composable
fun CheckScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(300.dp).height(200.dp),
            imageVector = ImageVector.vectorResource(R.drawable.onboarding_img1),
            contentDescription = "Изображение"
        )
        Text(
            text = "Ваш заказ успешно оплачен!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.green)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                navController.navigate(Route.MainScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.enable)
            )
        ) {
            Text(
                text = "Вернуться на главный экран",
                fontSize = 14.sp,
                color = colorResource(R.color.white)
            )
        }
    }
}