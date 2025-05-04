package com.diplom.mediresult.presentation.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diplom.mediresult.R
import com.diplom.mediresult.data.model.UserState
import com.diplom.mediresult.presentation.nvgraph.Route

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    navController: NavController
) {
    val backgroundGray = Color(0xFFF5F5F9)
    val textGray = Color(0xFF7E7E9A)

    val context = LocalContext.current

    var passwordVisible by remember { mutableStateOf(false) }

    val viewModel: SupabaseAuthViewModel = viewModel()

    val state = viewModel.state

    var currentUserState by remember { mutableStateOf("") }
    val userState by viewModel.userState
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Добро пожаловать!",
            fontSize = 20.sp,
            color = Color.Blue
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Text(
            text = "Войдите,чтобы воспользоваться функциями приложения"
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
                errorSupportingTextColor = textGray,
                errorLabelColor = textGray,
                errorCursorColor = textGray,
                errorSuffixColor = textGray,
                errorIndicatorColor = Color.Transparent,
                errorContainerColor = backgroundGray

            ),
            label = { Text("Вход по E-mail") },
            placeholder = { Text("example@mail.com")},
            value = state.email,
            onValueChange = {viewModel.onEvent(LoginFormEvent.EmailChanged(it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        val backgroundGray = Color(0xFFF5F5F9)
        val textGray = Color(0xFF7E7E9A)
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
            label = { Text("Пароль") },
            value = state.password,
            onValueChange = {viewModel.onEvent(LoginFormEvent.PasswordChanged(it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
        )
        Text(
            text = currentUserState,
            color = Color.Red
        )
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Row(
            modifier = Modifier.width(270.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.loginUser(
                        context = context,
                        navController = navController,
                        userEmail = viewModel.state.email,
                        userPassword = viewModel.state.password
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.enable),
                    disabledContainerColor = colorResource(R.color.disable)
                ),
            ) {
                Text(
                    text = "Далее",
                    color = Color.White
                )
            }
            Button(
                onClick = {
                    navController.navigate(route = Route.SinUpScreen.route)
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



        when(userState){
            is UserState.Loading -> {

            }
            is UserState.Error -> {
                val message = (userState as UserState.Error).message
                currentUserState = message
            }
            is UserState.Success -> {
                val message = (userState as UserState.Success).message
                currentUserState = message
            }
        }
    }
}




