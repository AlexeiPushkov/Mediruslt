package com.diplom.mediresult.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Analysis(
    @SerialName("id")
    val id: Int,
    @SerialName("Name")
    val name: String,
    @SerialName("Description")
    val description: String,
    @SerialName("Preparation")
    val preparation: String,
    @SerialName("Biometrial")
    val biometrial: String,
    @SerialName("Count_day")
    val countDay: Int,
    @SerialName("Price")
    val price: Int,
)
