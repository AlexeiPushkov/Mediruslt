package com.diplom.mediresult.presentation.main.pages.profile

import android.os.Build
import androidx.annotation.RequiresApi

data class ProfileState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val fio: String = "",
    val date: String = "" ,
    val gender: Boolean = true,
    val pathImg: String? = null,
)