package com.diplom.mediresult.data.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: String,
    @SerialName("fio")
    val fio: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("date")
    val dateOfBirth: String,
    @SerialName("gender")
    val gender: Boolean,
    @SerialName("path_img")
    val path: String?,
    @SerialName("id_Role")
    val idRole: Int
)