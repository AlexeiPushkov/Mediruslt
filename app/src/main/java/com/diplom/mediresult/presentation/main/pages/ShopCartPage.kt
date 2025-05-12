package com.diplom.mediresult.presentation.main.pages

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.diplom.mediresult.data.model.ShopCart
import com.diplom.mediresult.presentation.main.MainViewModel
import com.diplom.mediresult.presentation.nvgraph.Route


@Composable
fun ShopCartPage(
    navController: NavController
) {
    val mainViewModel: MainViewModel =viewModel()
    val context = LocalContext.current
    mainViewModel.getPrice(mainViewModel.shopCartState)
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Корзина",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                modifier = Modifier.clickable{
                    mainViewModel.shopCartState.removeAll(mainViewModel.shopCartState)
                    mainViewModel.saveShopCarts(context, mainViewModel.shopCartState)
                    mainViewModel.bage.intValue = 0
                    mainViewModel.saveBage(context, mainViewModel.bage.intValue)
                },
                imageVector = ImageVector.vectorResource(R.drawable.delete),
                contentDescription = "Удалить"
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        val context: Context = LocalContext.current
        val mainViewModel: MainViewModel = viewModel()
        mainViewModel.getShopCarts(context)
        if (mainViewModel.shopCartState.isEmpty()) {
            Text(
                text = "ShopCartPage"
            )
        }
        else{
            val setList = mutableListOf<ShopCart>()
            setList.addAll(mainViewModel.shopCartState)
            LazyColumn(
                modifier = Modifier.heightIn(650.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(setList) { shopCart ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        AnalysisCard(shopCart = shopCart, context)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Итог",
                    fontSize = 16.sp
                )
                Text(
                    text = "${mainViewModel.finalPrice.intValue} ₽",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                modifier = Modifier.width(300.dp),
                onClick = {
                    navController.navigate(Route.FormOrder.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.enable),
                    disabledContainerColor = colorResource(R.color.disable)
                ),
            ) {
                Text(
                    text = "Оформить заказ",
                    color = Color.White
                )
            }
        }
    }
}

@SuppressLint("ImplicitSamInstance")
@Composable
fun AnalysisCard(
    shopCart: ShopCart,
    context: Context,
    mainViewModel: MainViewModel = viewModel()
){
    Card(
        modifier = Modifier
            .fillMaxSize(0.9f)
            .fillMaxHeight(0.2f),
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
                    text = shopCart.name,
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
                        text = "${shopCart.price} ₽",
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedButton(
                    modifier = Modifier.align(Alignment.CenterVertically).width(130.dp),
                    onClick = {
                        mainViewModel.shopCartState.remove(ShopCart(
                            name = shopCart.name,
                            price = shopCart.price
                        ))
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