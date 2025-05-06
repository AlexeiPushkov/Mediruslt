package com.diplom.mediresult.presentation.main.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.mediresult.R
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.presentation.components.BottomSheet
import com.diplom.mediresult.presentation.components.SearchView
import com.diplom.mediresult.presentation.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalysisPage() {


    val mainViewModel: MainViewModel = viewModel()
    var analyses = remember { mutableStateListOf<Analysis>() }
    var searchQuery by remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            analyses.addAll(mainViewModel.getAnalysis())
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchView(
            hint = "Искать анализы",
            onQueryChange = { query ->
                searchQuery = query
            }
        )
        FilterableList(
            items = analyses,
            query = searchQuery
        )

    }
}

@Composable
fun FilterableList(
    items: List<Analysis>,
    query: String
) {
    val filteredItems = remember(query, items) {
        if (query.isEmpty()) {
            items
        } else {
            items.filter { item ->
                item.name.contains(query, ignoreCase = true)
            }
        }
    }

    LazyColumn() {
        items(filteredItems) { analysis ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                AnalysisCard(analysis = analysis)
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisCard(
    analysis: Analysis
){
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxSize(0.9f)
            .fillMaxHeight(0.15f),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp)
                .clickable{
                    coroutineScope.launch {
                        if (!bottomSheetScaffoldState.bottomSheetState.isVisible){
                            bottomSheetScaffoldState.bottomSheetState.show()
                        }else{
                            bottomSheetScaffoldState.bottomSheetState.hide()
                        }
                    }
                },
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = analysis.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.width(300.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start

                ) {
                    Text(
                        text = when (analysis.countDay) {
                            1 -> "${analysis.countDay} день"
                            in 2..4 -> "${analysis.countDay} дня"
                            else -> "${analysis.countDay} дней"
                        },
                        fontSize = 14.sp,
                    )
                    Text(
                        text = "${analysis.price} ₽",
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.enable),
                        disabledContainerColor = colorResource(R.color.disable)
                    ),
                ) {
                    Text(
                        text = "Добавить",
                        color = Color.White
                    )
                }
            }
        }
    }
}


