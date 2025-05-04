package com.diplom.mediresult.data.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Int,
    @SerialName("FIO")
    val fio: String,
    @SerialName("Email")
    val email: String,
    @SerialName("Phone")
    val phone: String,
    @SerialName("Password")
    val password: String,
    @SerialName("Date")
    val dateOfBirth: DateTimeUnit.DateBased,
    @SerialName("Gender")
    val gender: Boolean,
    @SerialName("Path_img")
    val path: String?,
    @SerialName("Id_Role")
    val idRole: Int
)