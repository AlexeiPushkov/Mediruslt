package com.diplom.mediresult.presentation.formOrder

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.presentation.components.CustomDatePicker
import com.diplom.mediresult.presentation.main.MainViewModel
import com.diplom.mediresult.presentation.nvgraph.Route
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormOrder(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val mainViewModel: MainViewModel = viewModel()
        val orderForm: FormOrderViewModel = viewModel()
        val backgroundGray = Color(0xFFF5F5F9)
        val textGray = Color(0xFF7E7E9A)
        val context = LocalContext.current
        val address = arrayOf(
            "Богатырский проспект, д.20",
            "Гражданский проспект, д.2",
            "Хасанская улица, д.1",
            "Благодатная улица, д.39",
            "Проспект Славы, д.87"
        )
        var expanded by remember { mutableStateOf(false) }
        var selectedText by remember { mutableStateOf(address[0]) }
        orderForm.onEvent(OrderFormEvent.AddressChanged(address[0]))
        var date = remember { mutableStateOf(LocalDate.now()) }
        orderForm.onEvent(OrderFormEvent.DateChange("${date.value} 10:00"))
        val state = orderForm.state
        val timesButtons = listOf<String>(
            "10:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
        )

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Оформление заказа",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Box(
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    label = {
                        Text(
                            text = "Выберите адрес ближайшего мед лаборатории",
                            color = textGray
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().width(300.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = textGray,
                        focusedTextColor = textGray,
                        focusedContainerColor = backgroundGray,
                        unfocusedContainerColor = backgroundGray,
                        focusedLabelColor = textGray,
                        unfocusedLabelColor = textGray,
                        focusedPlaceholderColor = textGray,
                        unfocusedPlaceholderColor = textGray,
                        cursorColor = textGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedSupportingTextColor = textGray,
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    address.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        CustomDatePicker(
            value = date.value,
            onValueChange = {
                date.value = it
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier.width(300.dp)
        ) {
            items(timesButtons) { timesButton ->
                Button(
                    modifier = Modifier.width(100.dp),
                    onClick = {
                        orderForm.onEvent(OrderFormEvent.DateChange("${date.value} $timesButton"))
                        Toast.makeText(context, "Ваша дата ${date.value} $timesButton", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.enable),
                        disabledContainerColor = colorResource(R.color.disable)
                    ),
                ) {
                    Text(
                        text = timesButton,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            modifier = Modifier
                .width(300.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = textGray,
                focusedTextColor = textGray,
                focusedContainerColor = backgroundGray,
                unfocusedContainerColor = backgroundGray,
                focusedLabelColor = textGray,
                unfocusedLabelColor = textGray,
                focusedPlaceholderColor = textGray,
                unfocusedPlaceholderColor = textGray,
                cursorColor = textGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedSupportingTextColor = textGray,
                errorTextColor = textGray,
                errorSupportingTextColor = textGray,
                errorLabelColor = textGray,
                errorCursorColor = textGray,
                errorSuffixColor = textGray,
                errorIndicatorColor = Color.Transparent,
                errorContainerColor = backgroundGray

            ),
            label = { Text("Коментарий") },
            placeholder = { Text("Можете оставить пожелания")},
            value = state.comment,
            onValueChange = {orderForm.onEvent(OrderFormEvent.CommentChanged(it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier.width(300.dp),
            onClick = {
                mainViewModel.getShopCarts(context)
                mainViewModel.getPrice(mainViewModel.shopCartState)
                if (orderForm.doOrder(
                    price = mainViewModel.finalPrice.intValue,
                    address = selectedText,
                    date = state.date,
                    comment = state.comment,
                    carts = mainViewModel.shopCartState
                )){
                    mainViewModel.shopCartState.removeAll(mainViewModel.shopCartState)
                    mainViewModel.saveShopCarts(context, mainViewModel.shopCartState)
                    mainViewModel.bage.intValue = 0
                    mainViewModel.saveBage(context, mainViewModel.bage.intValue)
                    navController.navigate(Route.OrderSuccess.route)
                }
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