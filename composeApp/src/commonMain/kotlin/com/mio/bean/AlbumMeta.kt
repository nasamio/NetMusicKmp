package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumMeta(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
)