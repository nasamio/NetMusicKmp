package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Al(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("pic")
    val pic: Long = 0,
    @SerialName("pic_str")
    val picStr: String? = null,
    @SerialName("picUrl")
    val picUrl: String = "",
    @SerialName("tns")
    val tns: List<String?> = listOf()
)