package com.diplom.mediresult.presentation.main.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.presentation.auth.SupabaseAuthViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePage(
    navController: NavController
) {
    val viewModel: SupabaseAuthViewModel = viewModel()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Start),
            onClick = {
                viewModel.logoutUser(
                    context = context,
                    navController = navController
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
        ) {
            Text(
                text = "Выйти",
                color = Color.Blue
            )
        }
    }
}