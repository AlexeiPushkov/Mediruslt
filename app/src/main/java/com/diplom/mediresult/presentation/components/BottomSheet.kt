package com.diplom.mediresult.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplom.mediresult.R
import com.diplom.mediresult.data.model.Analysis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    analysis: Analysis
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = rememberStandardBottomSheetState(
        SheetValue.Hidden))

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent =  {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text(
                    text = analysis.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Описание",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = analysis.description,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Подготовка",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = analysis.preparation,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column(){
                        Text(
                            text = "Результаты через",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = when (analysis.countDay) {
                                1 -> "${analysis.countDay} день"
                                in 2..4 -> "${analysis.countDay} дня"
                                else -> "${analysis.countDay} дней"
                            },
                            fontSize = 15.sp,
                        )
                    }
                    Column(){
                        Text(
                            text = "Биоматериал",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = analysis.biometrial,
                            fontSize = 15.sp,
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.enable),
                        disabledContainerColor = colorResource(R.color.disable)
                    ),
                ) {
                    Text(
                        text = "Добавить за ${analysis.price} ₽",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        },
        sheetPeekHeight = 0.dp
    ){}
}