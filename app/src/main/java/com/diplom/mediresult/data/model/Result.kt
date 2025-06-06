package com.diplom.mediresult.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("id")
    val id: Int,
    @SerialName("Id_user")
    val idUser: String,
    @SerialName("Id_analysis")
    val idAnalysis: Int,
    @SerialName("Path_file")
    val path: String,
)