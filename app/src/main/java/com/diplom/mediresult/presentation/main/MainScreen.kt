package com.diplom.mediresult.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.presentation.main.pages.AnalysisPage
import com.diplom.mediresult.presentation.main.pages.profile.ProfilePage
import com.diplom.mediresult.presentation.main.pages.resultats.ResultPage
import com.diplom.mediresult.presentation.main.pages.ShopCartPage

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val mainViewModel: MainViewModel = viewModel()
    val context = LocalContext.current
    mainViewModel.getBadge(context)
    val navItemList = listOf(
        NavItem(label = "Анализы", icon = R.drawable.analysis, 0),
        NavItem(label = "Корзина", icon = R.drawable.outline_local_grocery_store_24, mainViewModel.bage.intValue),
        NavItem(label = "Результат", icon = R.drawable.result, 0),
        //NavItem(label = "Поддержка", icon = R.drawable.support, 0),
        NavItem(label = "Профиль", icon = R.drawable.user, 0),
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
    )
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetContent =  {
            Spacer(modifier = Modifier.height(1.dp))
            Column(
                modifier = Modifier.height(450.dp).padding(16.dp).verticalScroll(scrollState)
            ) {
                Text(
                    text = mainViewModel.analysisState.value.name,
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
                    text = mainViewModel.analysisState.value.description,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Подготовка",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = mainViewModel.analysisState.value.preparation,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column {
                        Text(
                            text = "Результаты через",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = when (mainViewModel.analysisState.value.countDay) {
                                1 -> "${mainViewModel.analysisState.value.countDay} день"
                                in 2..4 -> "${mainViewModel.analysisState.value.countDay} дня"
                                else -> "${mainViewModel.analysisState.value.countDay} дней"
                            },
                            fontSize = 15.sp,
                        )
                    }
                    Column {
                        Text(
                            text = "Биоматериал",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = mainViewModel.analysisState.value.biometrial,
                            fontSize = 15.sp,
                        )
                    }
                }
            }
        },
        sheetPeekHeight = 300.dp
    ){
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = mainViewModel.mainIndex.intValue == index,
                            onClick = {
                                mainViewModel.mainIndex.intValue = index
                            },
                            icon = {
                                BadgedBox(
                                    badge = {
                                        if (navItem.bage > 0){
                                            Badge(
                                                containerColor = Color.Red
                                            ){
                                                Text(
                                                    text = navItem.bage.toString(),
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Image(
                                        imageVector = ImageVector.vectorResource(navItem.icon),
                                        contentDescription = "Icon",
                                        colorFilter = ColorFilter.tint(colorResource(if (mainViewModel.mainIndex.intValue == index) R.color.enable else R.color.enableIcon))
                                    )
                                }
                            },
                            label = {
                                Text(text = navItem.label)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = colorResource(R.color.enable),
                                unselectedTextColor = colorResource(R.color.enableIcon),
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        ) {innerPadding->
            MainScreenContent(
                navController = navController,
                selectedIndex = mainViewModel.mainIndex.intValue,
                padding = innerPadding,
                scaffoldState = scaffoldState
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenContent(
    navController: NavController,
    selectedIndex: Int,
    padding: PaddingValues,
    scaffoldState: BottomSheetScaffoldState
){
    when(selectedIndex){
        0-> AnalysisPage(
            scaffoldState = scaffoldState
        )
        1-> ShopCartPage(
            navController = navController
        )
        2-> ResultPage(
            navController = navController
        )
        /*3-> SupportPage(
            navController
        )*/
        3-> ProfilePage(
            navController
        )
    }
}

