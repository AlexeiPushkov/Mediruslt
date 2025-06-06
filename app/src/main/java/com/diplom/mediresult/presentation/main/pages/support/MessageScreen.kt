package com.diplom.mediresult.presentation.main.pages.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.R

@Composable
fun MessageScreen(
    navController: NavController
) {
    val messageViewModel: MessageViewModel = viewModel()
    LaunchedEffect(Unit){
        messageViewModel.getMessage()
        messageViewModel.realTime(this)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "Поддержка"
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            reverseLayout = true
        ) {
            items(messageViewModel.supports) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (it.idReceiver != null) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .background(color = colorResource(R.color.enable))
                            ){
                            Text(
                                text = it.message,
                                fontSize = 15.sp,
                                color = colorResource(R.color.white)
                            )
                        }
                    }
                    else{
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .background(color = colorResource(R.color.white))
                        ){
                            Text(
                                text = it.message,
                                fontSize = 15.sp,
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        TextField(
            value = "",
            onValueChange = {}
        )
    }
}