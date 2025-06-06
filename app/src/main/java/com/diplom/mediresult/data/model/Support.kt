package com.diplom.mediresult.data.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Support(
    @SerialName("id")
    val id: Int,
    @SerialName("Id_sender")
    val idSender: String,
    @SerialName("Id_receiver")
    val idReceiver: String?,
    @SerialName("Time")
    val time: DateTimeUnit.DateBased,
    @SerialName("Message")
    val message: String,

)
