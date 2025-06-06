package com.diplom.mediresult.presentation.auth.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.presentation.auth.SupabaseAuthViewModel
import com.diplom.mediresult.presentation.components.CustomDatePicker
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignInScreen(
    navController: NavController
) {
    val backgroundGray = Color(0xFFF5F5F9)
    val textGray = Color(0xFF7E7E9A)

    val context = LocalContext.current

    val viewModel: SupabaseAuthViewModel = viewModel()

    val state = viewModel.signUpstate
    val scrollState = rememberScrollState()
    val date = remember { mutableStateOf(LocalDate.now())}

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState,true).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Регистрация",
            fontSize = 20.sp,
            color = Color.Blue
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
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
                errorSupportingTextColor = Color.Red,
                errorLabelColor = textGray,
                errorCursorColor = textGray,
                errorSuffixColor = textGray,
                errorIndicatorColor = Color.Transparent,
                errorContainerColor = backgroundGray
            ),
            label = { Text("ФИО") },
            placeholder = { Text("Иванов Иван Иванович")},
            supportingText = {if (viewModel.validations[0])Text("ФИО должен содержать кириллицу и пробелы")},
            isError = viewModel.validations[0],
            value = state.fio,
            onValueChange = {
                viewModel.onEvent(SignUpFormEvent.FioChange(it))
                viewModel.validationsFIO()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
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
                errorSupportingTextColor = Color.Red,
                errorLabelColor = textGray,
                errorCursorColor = textGray,
                errorSuffixColor = textGray,
                errorIndicatorColor = Color.Transparent,
                errorContainerColor = backgroundGray
            ),
            label = { Text("E-mail") },
            placeholder = { Text("example@mail.com")},
            supportingText = {if (viewModel.validations[1])Text("Должен соответствовать структуре email и содержать 6 символов до '@'")},
            isError = viewModel.validations[1],
            value = state.email,
            onValueChange = {
                viewModel.onEvent(SignUpFormEvent.EmailChanged(it))
                viewModel.validationsEmail()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
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
                errorSupportingTextColor = Color.Red,
                errorLabelColor = textGray,
                errorCursorColor = textGray,
                errorSuffixColor = textGray,
                errorIndicatorColor = Color.Transparent,
                errorContainerColor = backgroundGray

            ),
            label = { Text("Пароль") },
            supportingText = {if (viewModel.validations[2])Text("Пароль должен быть не короче 6 символов")},
            isError = viewModel.validations[2],
            value = state.password,
            onValueChange = {
                viewModel.onEvent(SignUpFormEvent.PasswordChanged(it))
                viewModel.validationsPassword()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Text(
            text = "Пол"
        )
        RadioButtonSingleSelection()
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.acceptedTerms,
                    onCheckedChange = { viewModel.onEvent(SignUpFormEvent.TermsChange(it))},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Blue, // Purple color for checked state
                        uncheckedColor = Color(0xFF9C9EA1), // Gray color for unchecked state
                        checkmarkColor = Color.White
                    )
                )
                Text(text = "Я согласен с ", color = Color(0xFF9C9EA1), fontSize = 15.sp)
                Text(
                    text = "Политикой обработки персональных данных",
                    fontSize = 15.sp,
                    color = Color(0xFF3A82F7),
                    modifier = Modifier
                        .clickable {

                        }
                )
            }
            if(!state.acceptedTerms){
                Text(
                    text = "Необходимо принять соглашение",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (!viewModel.validations[0] && !viewModel.validations[1] && !viewModel.validations[2] && state.acceptedTerms)
                viewModel.signUp(
                    context = context,
                    navController = navController,
                    userEmail = viewModel.signUpstate.email,
                    userPassword = viewModel.signUpstate.password,
                    fio = viewModel.signUpstate.fio,
                    date = date.value.toString(),
                    gender = viewModel.signUpstate.gender.toString(),
                    pathImg = null,
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.enable),
                disabledContainerColor = colorResource(R.color.disable)
            ),
        ) {
            Text(
                text = "Зарегистрироваться",
                color = Color.White
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val viewModel: SupabaseAuthViewModel = viewModel()
    val radioOptions = listOf("Мужской", "Женский")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
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
                            if (selectedOption == "Мужской")
                                viewModel.onEvent(SignUpFormEvent.GenderChange(true))
                            else
                                viewModel.onEvent(SignUpFormEvent.GenderChange(false))
                            },
                        role = Role.RadioButton,
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        if (selectedOption == "Мужской")
                            viewModel.onEvent(SignUpFormEvent.GenderChange(true))
                        else
                            viewModel.onEvent(SignUpFormEvent.GenderChange(false))
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



