package com.diplom.mediresult.presentation.auth.login

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)