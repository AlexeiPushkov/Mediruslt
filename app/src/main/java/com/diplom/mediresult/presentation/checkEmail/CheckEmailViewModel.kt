package com.diplom.mediresult.presentation.checkEmail

import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CheckEmailViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set
    var isEnable by mutableStateOf(false)
        private set
    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            isEnable = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            // Email is considered erroneous until it completely matches EMAIL_ADDRESS.
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            isEnable = false
            false
        }
    }

    fun updateEmail(input: String) {
        email = input
    }
}