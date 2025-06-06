package com.diplom.mediresult.presentation.auth.signup

import android.os.Build
import androidx.annotation.RequiresApi

data class SignUpFormState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val email: String = "",
    val password: String = "",
    val date: String = "" ,
    val fio: String = "",
    val gender: Boolean = true,
    val acceptedTerms: Boolean = false,
    val pathImg: String? = null,
    val idRole: Int = 1,
    val isLoading: Boolean = false
)