package com.diplom.mediresult.presentation.main.pages

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.mediresult.R
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.data.model.ShopCart
import com.diplom.mediresult.presentation.components.CircleProgressBar
import com.diplom.mediresult.presentation.components.SearchView
import com.diplom.mediresult.presentation.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalysisPage(
    scaffoldState: BottomSheetScaffoldState
) {
    val mainViewModel: MainViewModel = viewModel()
    var analyses = remember { mutableStateListOf<Analysis>() }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            mainViewModel.loading.value = true
            analyses.addAll(mainViewModel.getAnalysis())
            mainViewModel.loading.value = false
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchView(
            hint = "Искать анализы",
            onQueryChange = { query ->
                searchQuery = query
            },
            scaffoldState = scaffoldState
        )
        FilterableList(
            items = analyses,
            query = searchQuery,
            scaffoldState = scaffoldState,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterableList(
    items: List<Analysis>,
    query: String,
    scaffoldState: BottomSheetScaffoldState,
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
    val mainViewModel: MainViewModel = viewModel()
    CircleProgressBar(isDisplayed = mainViewModel.loading.value)
    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.85f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(filteredItems) { analysis ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                AnalysisCard(analysis = analysis,scaffoldState = scaffoldState)
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ImplicitSamInstance")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisCard(
    analysis: Analysis,
    scaffoldState: BottomSheetScaffoldState,
){
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    mainViewModel.getShopCarts(context)
    Card(
        modifier = Modifier
            .fillMaxSize(0.9f)
            .fillMaxHeight(0.2f)
            .clickable {
                coroutineScope.launch {
                    if (!scaffoldState.bottomSheetState.isVisible) {
                        mainViewModel.analysisState.value = analysis
                        scaffoldState.bottomSheetState.show()
                    } else {
                        mainViewModel.analysisState.value = analysis
                        scaffoldState.bottomSheetState.hide()
                    }
                }
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
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
                if (!mainViewModel.shopCartState.contains(
                        ShopCart(
                            name = analysis.name,
                            price = analysis.price
                        )
                    )
                ) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(130.dp),
                        onClick = {

                            mainViewModel.shopCartState.add(
                                ShopCart(
                                    name = analysis.name,
                                    price = analysis.price
                                )
                            )
                            mainViewModel.saveShopCarts(context, mainViewModel.shopCartState)
                            mainViewModel.bage.intValue++
                            mainViewModel.saveBage(context, mainViewModel.bage.intValue)

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
                }else{
                    OutlinedButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(130.dp),
                        onClick = {
                            mainViewModel.shopCartState.remove(
                                ShopCart(
                                    name = analysis.name,
                                    price = analysis.price
                                )
                            )
                            mainViewModel.saveShopCarts(context, mainViewModel.shopCartState)
                            mainViewModel.bage.intValue--
                            mainViewModel.saveBage(context, mainViewModel.bage.intValue)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = colorResource(R.color.white),
                        ),
                        border = BorderStroke(width = 1.dp, color = colorResource(R.color.enable))
                    ) {
                        Text(
                            text = "Убрать",
                            color = colorResource(R.color.enable)
                        )
                    }
                }
            }
        }
    }
}


