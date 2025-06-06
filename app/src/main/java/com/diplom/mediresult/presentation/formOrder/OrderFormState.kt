package com.diplom.mediresult.presentation.formOrder

import android.os.Build
import androidx.annotation.RequiresApi

data class OrderFormState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val address: String = "",
    val date: String = "" ,
    val comment: String = "",
)