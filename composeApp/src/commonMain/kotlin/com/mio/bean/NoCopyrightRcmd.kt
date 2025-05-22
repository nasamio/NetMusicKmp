package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoCopyrightRcmd(
    @SerialName("expInfo")
    val expInfo: String? = null,
    @SerialName("songId")
    val songId: String? = null,
    @SerialName("thirdPartySong")
    val thirdPartySong: String? = null,
    @SerialName("type")
    val type: Int = 0,
    @SerialName("typeDesc")
    val typeDesc: String = ""
)