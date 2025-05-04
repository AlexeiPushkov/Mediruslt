package com.diplom.mediresult.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    @SerialName("id")
    val id: Int,
    @SerialName("Name")
    val name: String
)