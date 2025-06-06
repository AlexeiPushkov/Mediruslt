package com.diplom.mediresult.presentation.main.pages.resultats

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.data.model.Analysis
import com.diplom.mediresult.data.model.Result
import com.diplom.mediresult.presentation.main.MainViewModel
import com.diplom.mediresult.presentation.nvgraph.Route
import com.diplom.mediresult.presentation.pdf.PDFViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResultPage(
    navController: NavController
) {
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel()
    val viewModel: ResultsViewModel = viewModel()
    var results = remember { mutableStateListOf<Result>() }
    LaunchedEffect(Unit) {
        mainViewModel.loading.value = true
        results.addAll(viewModel.getResults())
        mainViewModel.loading.value = false
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Результаты",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        if (results.isEmpty()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "История результатов пуста"
                )
            }
        }
        else {
            LazyColumn(
                modifier = Modifier.height(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(results.reversed()) { result ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        ResultCard(resultCard = result, context, navController)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ImplicitSamInstance")
@Composable
fun ResultCard(
    resultCard: Result,
    context: Context,
    navController: NavController,
    mainViewModel: MainViewModel = viewModel()
){
    var analysis = remember { mutableStateOf<Analysis>(Analysis(0,"","","","",0,0)) }
    val pdfViewModel: PDFViewModel = viewModel()
    LaunchedEffect(Unit) {
        analysis.value = mainViewModel.getAnalyses(resultCard.idAnalysis)
    }
    Card(
        modifier = Modifier
            .fillMaxSize(0.9f)
            .fillMaxHeight(0.2f)
            .clickable{
                pdfViewModel.savePath(context, data = resultCard.path)
                navController.navigate(Route.PDFScreen.route)
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = analysis.value.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}