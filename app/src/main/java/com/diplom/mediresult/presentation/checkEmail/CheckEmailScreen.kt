package com.diplom.mediresult.presentation.checkEmail

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diplom.mediresult.presentation.checkEmail.CheckEmailViewModel

@Composable
fun CheckEmailScreen() {
}


@Preview
@Composable
fun ValidateInput() {
    val emailViewModel: CheckEmailViewModel = viewModel< CheckEmailViewModel>()
    ValidatingInputTextField(
        email = emailViewModel.email,
        updateState = { input -> emailViewModel.updateEmail(input) },
        validatorHasErrors = emailViewModel.emailHasErrors,
    )
}

@Composable
fun ValidatingInputTextField(
    email: String,
    updateState: (String) -> Unit,
    validatorHasErrors: Boolean,
) {
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
        label = { Text("Вход по E-mail") },
        placeholder = { Text("example@mail.com")},
        value = email,
        onValueChange = updateState,
        isError = validatorHasErrors,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        supportingText = {
            if (validatorHasErrors) {
                Text("Некорректный E-mail")
            }
        }
    )
}