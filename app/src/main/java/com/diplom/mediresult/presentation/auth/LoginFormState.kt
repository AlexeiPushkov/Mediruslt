package com.diplom.mediresult.presentation.auth

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)