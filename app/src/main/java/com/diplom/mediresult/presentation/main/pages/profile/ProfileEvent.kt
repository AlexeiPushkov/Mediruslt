package com.diplom.mediresult.presentation.main.pages.profile

sealed class ProfileEvent {
    data class FioChange(val fio: String) : ProfileEvent()
    data class DateChange(val date: String) : ProfileEvent()
    data class GenderChange(val gender: Boolean) : ProfileEvent()
}