package com.diplom.mediresult.data.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order (
    @SerialName("Id_user")
    val idUser: String,
    @SerialName("Description")
    val description: String,
    @SerialName("Price")
    val price: Int,
    @SerialName("Address")
    val address: String,
    @SerialName("Date")
    val date: String,
    @SerialName("Comment")
    val comment: String,
)