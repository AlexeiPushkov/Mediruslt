package com.diplom.mediresult.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.presentation.main.pages.AnalysisPage
import com.diplom.mediresult.presentation.main.pages.ProfilePage
import com.diplom.mediresult.presentation.main.pages.ResultPage
import com.diplom.mediresult.presentation.main.pages.ShopCartPage
import com.diplom.mediresult.presentation.main.pages.SupportPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController
) {
    val navItemList = listOf(
        NavItem(label = "Анализы", icon = R.drawable.analysis),
        NavItem(label = "Корзина", icon = R.drawable.outline_local_grocery_store_24),
        NavItem(label = "Результат", icon = R.drawable.result),
        NavItem(label = "Поддержка", icon = R.drawable.support),
        NavItem(label = "Профиль", icon = R.drawable.user),
    )
    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Image(
                                imageVector = ImageVector.vectorResource(navItem.icon),
                                contentDescription = "Icon",
                                colorFilter = ColorFilter.tint(colorResource(if (selectedIndex == index) R.color.enable else R.color.enableIcon))
                            )
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
    ) {
        MainScreenContent(
            navController,
            selectedIndex
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenContent(
    navController: NavController,
    selectedIndex: Int
){
    when(selectedIndex){
        0-> AnalysisPage()
        1-> ShopCartPage()
        2-> ResultPage()
        3-> SupportPage()
        4-> ProfilePage(
            navController
        )
    }
}

