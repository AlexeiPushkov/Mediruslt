package com.diplom.mediresult.presentation.main.pages.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.presentation.auth.SupabaseAuthViewModel
import com.diplom.mediresult.presentation.components.CustomDatePicker
import com.diplom.mediresult.presentation.main.MainViewModel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePage(
    navController: NavController
) {
    val viewModel: SupabaseAuthViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val context = LocalContext.current
    val backgroundGray = Color(0xFFF5F5F9)
    val textGray = Color(0xFF7E7E9A)
    val date = remember { mutableStateOf(LocalDate.now())}
    val state = mainViewModel.state
    LaunchedEffect(key1 = true) {
        val user = mainViewModel.getUser()
        mainViewModel.onEvent(ProfileEvent.FioChange(user.fio))
        mainViewModel.onEvent(ProfileEvent.GenderChange(user.gender))
        mainViewModel.onEvent(ProfileEvent.DateChange(user.dateOfBirth))
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.align(Alignment.End),
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
        Text(
            text = "Профиль",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        /*Image(
            modifier = Modifier.height(120.dp).width(140.dp).clip(CircleShape),
            painter = painterResource(R.drawable.nameless_image),
            contentDescription = "Профильная картинка",
        )*/
        Spacer(modifier = Modifier.height(20.dp))
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
                focusedSupportingTextColor = Color.Red,
                errorTextColor = textGray,
                errorSupportingTextColor = Color.Red,
                errorLabelColor = textGray,
                errorCursorColor = textGray,
                errorSuffixColor = textGray,
                errorIndicatorColor = Color.Transparent,
                errorContainerColor = backgroundGray

            ),
            label = { Text("Фио") },
            placeholder = { Text("Иванов Иван Иванович")},
            value = state.fio,
            onValueChange = {
                mainViewModel.onEvent(ProfileEvent.FioChange(it))
                mainViewModel.validationsFIO()
            },
            supportingText = {if (mainViewModel.validations.value)Text("ФИО должен содержать кириллицу и пробелы")},
            isError = mainViewModel.validations.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        CustomDatePicker(
            value = date.value,
            onValueChange = {
                date.value = it
            }
        )
        Text(
            text = "Ваша текущая дата рождения ${mainViewModel.state.date}"
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Text(
            text = "Пол"
        )
        RadioButtonSingleSelection()
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (!mainViewModel.validations.value) {
                    mainViewModel.updateUserData(
                        fio = mainViewModel.state.fio,
                        date = if (date.value != LocalDate.now()) date.value.toString() else mainViewModel.state.date,
                        gender = mainViewModel.state.gender.toString(),
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.enable),
                disabledContainerColor = colorResource(R.color.disable)
            ),
        ) {
            Text(
                text = "Сохранить",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = mainViewModel.success.value,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val mainViewModel: MainViewModel = viewModel()
    val radioOptions = listOf("Мужской", "Женский")

    val (selectedOption, onOptionSelected) = remember { mutableStateOf( if (mainViewModel.state.gender) radioOptions[0] else radioOptions[1]) }
    Column(modifier.selectableGroup().width(300.dp)) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            if (text == "Мужской")
                                mainViewModel.onEvent(ProfileEvent.GenderChange(true))
                            else
                                mainViewModel.onEvent(ProfileEvent.GenderChange(false))
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        if (text == "Мужской") mainViewModel.onEvent(ProfileEvent.GenderChange(true)) else mainViewModel.onEvent(ProfileEvent.GenderChange(false))
                    },
                    colors =  RadioButtonDefaults.colors(
                        selectedColor = Color.Blue
                    )
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}