package com.diplom.mediresult.presentation.formOrder

sealed class OrderFormEvent {
    data class AddressChanged(val address: String) : OrderFormEvent()
    data class CommentChanged(val comment: String) : OrderFormEvent()
    data class DateChange(val date: String) : OrderFormEvent()
}