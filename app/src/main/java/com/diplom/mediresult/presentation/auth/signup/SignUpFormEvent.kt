package com.diplom.mediresult.presentation.auth.signup

sealed class SignUpFormEvent {
    data class EmailChanged(val email: String) : SignUpFormEvent()
    data class PasswordChanged(val password: String) : SignUpFormEvent()
    data class FioChange(val fio: String) : SignUpFormEvent()
    data class DateChange(val date: String) : SignUpFormEvent()
    data class GenderChange(val gender: Boolean) : SignUpFormEvent()
    data class TermsChange(val terms: Boolean) : SignUpFormEvent()
}