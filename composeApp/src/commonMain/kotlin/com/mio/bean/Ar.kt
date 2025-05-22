package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ar(
    @SerialName("alias")
    val alias: List<String?> = listOf(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("tns")
    val tns: List<String?> = listOf()
)