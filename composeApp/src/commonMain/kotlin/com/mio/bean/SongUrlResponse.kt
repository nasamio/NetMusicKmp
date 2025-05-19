package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongUrlResponse(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("data")
    val `data`: List<Data> = listOf()
)